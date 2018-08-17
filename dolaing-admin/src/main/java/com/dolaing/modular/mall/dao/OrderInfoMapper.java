package com.dolaing.modular.mall.dao;

import com.dolaing.modular.mall.model.OrderInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.system.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zx
 * @since 2018-08-04
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    List<OrderInfoVo> queryOrdersByUser(Map map);
    Integer queryOrdersCountByUser(User user);
    Boolean batchDeliver(Map map);
    Boolean batchReceive(Map map);
    Integer saveOrderInfo(OrderInfo orderInfo);

    /**
     * 根据订单id查询订单
     * @param orderId
     * @return
     */
    OrderInfoVo queryOrderById(@Param("orderId") Integer orderId);

    /**
     * 修改订单卖家信息
     * @param orderInfo
     * @return
     */
    Boolean updateSellerMsgById(OrderInfo orderInfo);
    /**
     * 修改订单农户信息
     * @param orderInfo
     * @return
     */
    Boolean updateFarmerMsgById(OrderInfo orderInfo);
}
