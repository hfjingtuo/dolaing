package com.dolaing.modular.api.member;

import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.core.shiro.ShiroKit;
import com.dolaing.core.support.HttpKit;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.api.base.Result;
import com.dolaing.modular.api.enums.PayEnum;
import com.dolaing.modular.mall.service.IOrderGoodsService;
import com.dolaing.modular.mall.service.IOrderInfoService;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.modular.system.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "订单查询")
    @RequestMapping("/queryRecordsByUser")
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
    public Result batchDeliver(@RequestParam String ids) {
        String token = JwtTokenUtil.getToken(HttpKit.getRequest());
        String account = JwtTokenUtil.getAccountFromToken(token);
        orderInfoService.batchDeliver(account, ids);
        return render(true);
    }

    @ApiOperation(value = "批量收货")
    @RequestMapping("/batchDeliver")
    public Result batchReceive(@RequestParam String ids) {
        String token = JwtTokenUtil.getToken(HttpKit.getRequest());
        String account = JwtTokenUtil.getAccountFromToken(token);
        orderInfoService.batchReceive(account, ids);
        return render(true);
    }

    @ApiOperation(value = "订单支付")
    @RequestMapping("/pay")
    public Result pay(@RequestParam String orderId, @RequestParam String payPassword) {
        String token = JwtTokenUtil.getToken(HttpKit.getRequest());
        String account = JwtTokenUtil.getAccountFromToken(token);
        User user = new User().selectOne("account = {0}", account);
        UserPayAccount userPayAccount = new UserPayAccount().selectOne("user_id = {0}", account);
        if (null == userPayAccount) {
            return render(null, PayEnum.NO_PAY_ACCOUNT);
        }

        String payPasswordMd5 = ShiroKit.md5(payPassword, String.valueOf(user.getId()));
        if (!user.getPayPassword().equals(payPasswordMd5)) {
            render(null, PayEnum.PAY_PASSWORD_ERR);
        }
        orderInfoService.payOrder(userPayAccount, orderId);
        return render(true);
    }


}
