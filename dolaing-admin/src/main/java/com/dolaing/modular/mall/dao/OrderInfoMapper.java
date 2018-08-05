package com.dolaing.modular.mall.dao;

import com.dolaing.modular.mall.model.OrderInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.system.model.User;

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

    Integer saveOrderInfo(OrderInfo orderInfo);
}
