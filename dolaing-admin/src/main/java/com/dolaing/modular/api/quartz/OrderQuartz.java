package com.dolaing.modular.api.quartz;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.dolaing.core.common.constant.Const;
import com.dolaing.modular.mall.model.MallGoods;
import com.dolaing.modular.mall.model.OrderGoods;
import com.dolaing.modular.mall.model.OrderInfo;
import com.dolaing.modular.mall.service.IOrderInfoService;
import com.dolaing.modular.member.model.UserAccountRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    Logger logger = LoggerFactory.getLogger(OrderQuartz.class);
    @Autowired
    private IOrderInfoService orderInfoService;

    /**
     * 查询所有确认订单超过30分钟未付款订单 置为失效
     */
    @Scheduled(cron = "*/10 * * * * ?") //每分钟执行一次
    public void setOrderStatus() {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, -30);
        Date beforeDate = nowTime.getTime();//30分钟前的时间

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<更新订单状态、更新商品库存【" + nowTime + "】>>>>>>>>>>>>>>>>>>>>>");

        Wrapper<OrderInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("order_status", Const.ORDER_STATUS_UNCONFIRMED);
        wrapper.eq("pay_status", Const.ORDER_PAY_STATUS);
        wrapper.lt("create_time", beforeDate);
        List<OrderInfo> list = new OrderInfo().selectList(wrapper);
        if (!list.isEmpty() && list.size() != 0) {
            list.forEach(obj -> {
                /*******订单失效 新的库存=失效订单商品数量+库存******/
                Wrapper<OrderGoods> orderGoodsWrapper = new EntityWrapper<>();
                orderGoodsWrapper.eq("order_id", obj.getId());
                OrderGoods orderGoods = new OrderGoods().selectOne(orderGoodsWrapper);
                if (orderGoods != null) {
                    MallGoods mallGoods = new MallGoods().selectById(orderGoods.getGoodsId());
                    if (mallGoods != null) {
                        mallGoods.setGoodsNumber(mallGoods.getGoodsNumber() + orderGoods.getGoodsNumber());
                        mallGoods.updateById();//更新商品库存
                        logger.debug("更新商品：" + orderGoods.getGoodsId() + "库存");
                    }
                }
                obj.setOrderStatus(Const.ORDER_STATUS_EXPIRE);
                logger.debug("订单号：" + obj.getOrderSn() + "订单失效");
                obj.updateById();//更新订单状态
            });
        }
    }


    /**
     * 检查订单未支付给农户和卖家尾款的订单，执行支付尾款操作
     */
    @Scheduled(cron = "0 */5 * * * ?") //每十分钟执行一次
    public void payOrderDepositOrBalanceTask() {
        logger.debug(new Date() + "执行定时任务[查询未支付给农户和卖家尾款的订单，执行支付操作]........");
        //查询尚未支付定金给卖家订单
        List<OrderInfo> sellerDepositOrders = new OrderInfo().selectList("order_status = 1 " +
                " and pay_status = 1 and seller_receive_status = 0 and seller_money_received = 0 ", null);

        for (OrderInfo sellerOrder : sellerDepositOrders) {
            orderInfoService.payOrderDepositOrBalance(1, 1, sellerOrder);
        }

        //查询尚未支付定金给农户的订单
        List<OrderInfo> farmerDepositOrders = new OrderInfo().selectList("order_status = 1  " +
                " and pay_status = 1 and farmer_receive_status = 0 and farmer_money_received = 0 ", null);

        for (OrderInfo farmerOrder : farmerDepositOrders) {
            orderInfoService.payOrderDepositOrBalance(1, 2, farmerOrder);
        }

        //查询尚未支付尾款给卖家订单
        List<OrderInfo> sellerBalanceOrders = new OrderInfo().selectList("order_status = 1 and shipping_status =2 " +
                "and pay_status = 1 and seller_receive_status = 2 and seller_money_received < seller_receivable_amount", null);

        for (OrderInfo sellerOrder : sellerBalanceOrders) {
            orderInfoService.payOrderDepositOrBalance(2, 1, sellerOrder);
        }

        //查询尚未支付尾款给农户的订单
        List<OrderInfo> farmerBalanceOrders = new OrderInfo().selectList("order_status = 1 and shipping_status =2 " +
                "and pay_status = 1 and farmer_receive_status = 2 and farmer_money_received < farmer_receivable_amount", null);

        for (OrderInfo farmerOrder : farmerBalanceOrders) {
            orderInfoService.payOrderDepositOrBalance(2, 2, farmerOrder);
        }
    }

    /**
     * 查询转出中的订单状态
     */
    @Scheduled(cron = "0 */7 * * * ?") //每五分钟执行一次
    public void queryOrderTransStatusTask() {
        logger.debug(new Date() + "执行定时任务[查询转出中的订单状态]........");
        //查询尚未转出完成的订单
        List<UserAccountRecord> list = new UserAccountRecord().selectList("status = {0} and ( process_type = {1} or process_type = {2} )", 0, 1, 2);
        for (UserAccountRecord userAccountRecord : list) {
            logger.debug("查询订单状态-交易流水号为：" + userAccountRecord.getSeqId());
            orderInfoService.queryOrderTransStatusTask(userAccountRecord);
        }
    }


}