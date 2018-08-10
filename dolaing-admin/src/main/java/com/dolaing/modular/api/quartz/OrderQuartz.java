package com.dolaing.modular.api.quartz;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.dolaing.core.common.constant.Const;
import com.dolaing.modular.mall.model.OrderInfo;
import com.dolaing.modular.mall.service.IOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Author: zx
 * Date: Created in 2018/08/8 11:44
 * Copyright: Copyright (c) 2018
 * Description： 订单定时器
 */
@Component
public class OrderQuartz {
    @Autowired
    private IOrderInfoService orderInfoService;

    /**
     * 查询所有未确认订单 超过30分钟订单置为失效
     */
    //@Scheduled(cron = "* */1 * * * ?") //每分钟执行一次
    public void setOrderStatus() {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, -30);//30分钟后的时间
        Date beforeDate = nowTime.getTime();//30分钟前的时间

        System.out.println("now：" + new Date());
        System.out.println("beforeDate：" + beforeDate);

        Wrapper<OrderInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("order_status", Const.ORDER_STATUS_UNCONFIRMED);
        wrapper.lt("create_time", beforeDate);
        List<OrderInfo> list = new OrderInfo().selectList(wrapper);
        if (!list.isEmpty() && list.size() != 0) {
            list.forEach(obj -> {
                System.out.println(obj.getOrderSn() + "--" + obj.getOrderStatus() + "--" + obj.getCreateTime());
                obj.setOrderStatus(Const.ORDER_STATUS_EXPIRE);
                obj.updateById();
            });
        }
    }


    /**
     * 检查已收货订单但是未支付给农户和卖家尾款的订单，执行支付尾款操作
     */
    @Scheduled(cron = "* */5 * * * ?") //每十分钟执行一次
    public void payOrderDepositOrBalanceTask() {
        System.out.println(new Date()+"执行定时任务........");
        //查询尚未支付定金给卖家订单
        List<OrderInfo> sellerDepositOrders = new OrderInfo().selectList("order_status = 1 " +
                " and pay_status = 1 and seller_receive_status = 0 and seller_money_received = 0 ",null);

        for(OrderInfo sellerOrder : sellerDepositOrders){
            orderInfoService.payOrderDepositOrBalance(1,1,sellerOrder);
        }

        //查询尚未支付定金给农户的订单
        List<OrderInfo> farmerDepositOrders = new OrderInfo().selectList("order_status = 1  " +
                " and pay_status = 1 and farmer_receive_status = 0 and farmer_money_received = 0 ",null);

        for(OrderInfo farmerOrder : farmerDepositOrders){
            orderInfoService.payOrderDepositOrBalance(1,2,farmerOrder);
        }

        //查询尚未支付尾款给卖家订单
        List<OrderInfo> sellerBalanceOrders = new OrderInfo().selectList("order_status = 1 and shipping_status =3 " +
                "and pay_status = 1 and seller_receive_status = 2 and seller_money_received < seller_receivable_amount",null);

        for(OrderInfo sellerOrder : sellerBalanceOrders){
            orderInfoService.payOrderDepositOrBalance(2,1,sellerOrder);
        }

        //查询尚未支付尾款给农户的订单
        List<OrderInfo> farmerBalanceOrders = new OrderInfo().selectList("order_status = 1 and shipping_status =3 " +
                "and pay_status = 1 and farmer_receive_status = 2 and farmer_money_received < farmer_receivable_amount",null);

        for(OrderInfo farmerOrder : farmerBalanceOrders){
            orderInfoService.payOrderDepositOrBalance(2,2,farmerOrder);
        }
    }





}