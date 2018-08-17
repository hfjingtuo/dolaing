package com.dolaing.modular.api.member;

import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.core.common.annotion.AuthAccess;
import com.dolaing.core.shiro.ShiroKit;
import com.dolaing.core.support.HttpKit;
import com.dolaing.core.util.TokenUtil;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.api.base.Result;
import com.dolaing.modular.api.enums.PayEnum;
import com.dolaing.modular.mall.model.OrderInfo;
import com.dolaing.modular.mall.service.IOrderGoodsService;
import com.dolaing.modular.mall.service.IOrderInfoService;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.member.model.UserAccountRecord;
import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.modular.redis.service.RedisTokenService;
import com.dolaing.modular.system.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:张立华
 * @Description: 订单记录
 * @Date：Created in 19:21 2018/8/4
 * @Modified By:
 */
@RestController
@RequestMapping("/dolaing/orderRecord")
public class OrderRecordApi extends BaseApi {
    @Autowired
    private IOrderInfoService orderInfoService;
    @Autowired
    private IOrderGoodsService orderGoodsService;
    @Autowired
    private RedisTokenService redisTokenService;

    @ApiOperation(value = "订单查询")
    @RequestMapping("/queryRecordsByUser")
    @AuthAccess
    public Result queryRecordsByUser(@RequestParam String userId, @RequestParam Integer pageSize, @RequestParam Integer pageNo) {
        User user = new User().selectOne("account = {0}", userId);
        Page<OrderInfoVo> page = new Page(pageNo, pageSize);
        if (user != null) {
            page = orderInfoService.queryOrdersByUser(page, user);
            if (page.getRecords() != null) {
                for (OrderInfoVo orderRecordVo : page.getRecords()) {
                    orderRecordVo.setOrderGoodsVos(orderGoodsService.queryOrderGoodsByOrderId(orderRecordVo.getId()));
                }
            }
        }
        return render(page);
    }


    @ApiOperation(value = "批量发货")
    @RequestMapping("/batchDeliver")
    @AuthAccess
    public Result batchDeliver(@RequestParam String ids) {
        String token = TokenUtil.getToken(HttpKit.getRequest());
        String account = redisTokenService.getTokenModel(token).getAccount();
        orderInfoService.batchDeliver(account, ids);
        return render(true);
    }

    @ApiOperation(value = "批量收货")
    @RequestMapping("/batchReceive")
    @AuthAccess
    public Result batchReceive(@RequestParam String ids) {
        String token = TokenUtil.getToken(HttpKit.getRequest());
        String account = redisTokenService.getTokenModel(token).getAccount();
        orderInfoService.batchReceive(account, ids);
        return render(true);
    }

    @ApiOperation(value = "订单支付")
    @RequestMapping("/pay")
    @AuthAccess
    public Result pay(@RequestParam String orderId, @RequestParam String payPassword) {
        String token = TokenUtil.getToken(HttpKit.getRequest());
        String account = redisTokenService.getTokenModel(token).getAccount();
        User user = new User().selectOne("account = {0}", account);
        OrderInfo orderInfo = new OrderInfo().selectOne("id = {0} " ,orderId) ;
        if(orderInfo == null ){
            return render(null, PayEnum.NO_ORDER);
        }else if(!orderInfo.getUserId().equals(account)){
            return render(null, PayEnum.NO_RIGHT);
        }else if(orderInfo.getOrderStatus() != 1){
            return render(null, PayEnum.NOT_PAY_STATUS);
        }else if(orderInfo.getPayStatus() == 1){
            return render(null, PayEnum.PAIED);
        }
        UserPayAccount userPayAccount = new UserPayAccount().selectOne("user_id = {0}", account);
        if (null == userPayAccount) {
            return render(null, PayEnum.NO_PAY_ACCOUNT);
        }

        String payPasswordMd5 = ShiroKit.md5(payPassword, String.valueOf(user.getId()));
        if (!user.getPayPassword().equals(payPasswordMd5)) {
            return render(null, PayEnum.PAY_PASSWORD_ERR);
        }
        Map map = orderInfoService.payOrder(userPayAccount, orderId);
        if(!map.get("code").toString().equals("1000")){
            return render(null,map.get("code").toString(),map.get("msg").toString());
        }
        return render(true);
    }


    @ApiOperation(value = "买家订单详情")
    @GetMapping("/detail")
    @AuthAccess
    public Result detail(@RequestParam Integer orderId){
        HashMap<String, Object> result = new HashMap<>();
        OrderInfoVo orderInfoVo = orderInfoService.queryOrderById(orderId);
        if (orderInfoVo != null){
            orderInfoVo.setOrderGoodsVos(orderGoodsService.queryOrderGoodsByOrderId(orderId));
        }
        UserAccountRecord userAccountRecord = new UserAccountRecord().selectOne("user_id = {0} and payment_id ={1} and status ={2} and source_id = {3}",
                orderInfoVo.getUserId(), 0,1,orderInfoVo.getOrderSn());
        result.put("orderInfoVo",orderInfoVo);
        result.put("userAccountRecord",userAccountRecord);
        return render(result);
    }


}
