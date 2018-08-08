package com.dolaing.modular.mall.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.modular.api.base.IResult;
import com.dolaing.modular.mall.model.OrderInfo;
import com.baomidou.mybatisplus.service.IService;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.modular.system.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    IResult payOrder(UserPayAccount userPayAccount , String orderId );

    /**
     * 根据订单id查询订单
     * @param orderId
     * @return
     */
    OrderInfoVo queryOrderById(@Param("orderId") Integer orderId);

    IResult payOrderBalance(String orderId ) ;
}
