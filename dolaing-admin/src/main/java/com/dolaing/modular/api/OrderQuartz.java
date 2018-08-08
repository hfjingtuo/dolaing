package com.dolaing.modular.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.dolaing.core.common.constant.Const;
import com.dolaing.modular.mall.model.OrderInfo;
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
}