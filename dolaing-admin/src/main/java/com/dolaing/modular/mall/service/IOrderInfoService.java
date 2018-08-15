package com.dolaing.modular.mall.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.modular.api.base.IResult;
import com.dolaing.modular.mall.model.OrderInfo;
import com.baomidou.mybatisplus.service.IService;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.member.model.UserAccountRecord;
import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.modular.system.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zx
 * @since 2018-08-04
 */
public interface IOrderInfoService extends IService<OrderInfo> {
    Page queryOrdersByUser(Page page , User user);

    Boolean batchDeliver(String account , String ids);

    Boolean batchReceive(String account , String ids);

    Integer saveOrderInfo(OrderInfo orderInfo);

    Map payOrder(UserPayAccount userPayAccount , String orderId );

    /**
     * 根据订单id查询订单
     * @param orderId
     * @return
     */
    OrderInfoVo queryOrderById(@Param("orderId") Integer orderId);

    /**
     * @Author: 张立华
     * @Description: 支付定金或者尾款 给卖家或者农户
     * @params: * opType[1:定金 2:尾款]  roleType[1:卖家 2:农户]  orderInfo:订单
     * @return: *
     * @Date: 1:00 2018/8/10
     */
    void payOrderDepositOrBalance(Integer opType , Integer roleType ,OrderInfo orderInfo) ;


    void queryOrderTransStatusTask(UserAccountRecord userAccountRecord );
}
