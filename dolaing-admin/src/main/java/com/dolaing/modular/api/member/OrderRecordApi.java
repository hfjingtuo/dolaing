package com.dolaing.modular.api.member;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.api.base.Result;
import com.dolaing.modular.mall.model.OrderInfo;
import com.dolaing.modular.mall.service.IOrderGoodsService;
import com.dolaing.modular.mall.service.IOrderInfoService;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.mall.vo.OrderRecordVo;
import com.dolaing.modular.member.model.UserAccountRecord;
import com.dolaing.modular.member.service.IAccountRecordService;
import com.dolaing.modular.system.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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

    @ApiOperation(value = "订单查询")
    @RequestMapping("/queryRecordsByUser")
    public Result queryRecordsByUser(@RequestParam String userId,@RequestParam Integer pageSize, @RequestParam Integer pageNo){
        User user = new User().selectOne("account = {0}" , userId);
        Page<OrderInfoVo> page = new Page(pageNo,pageSize) ;
        if(user != null ){
            page = orderInfoService.queryOrdersByUser(page,user);
            if(page.getRecords() != null ){
                for(OrderInfoVo orderRecordVo : page.getRecords()){
                    orderRecordVo.setOrderGoodsVos(orderGoodsService.queryOrderGoodsByOrderId(orderRecordVo.getId()));
                }
            }
        }
        return render(page);
    }
}
