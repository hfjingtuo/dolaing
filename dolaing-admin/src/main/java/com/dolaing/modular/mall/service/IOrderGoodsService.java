package com.dolaing.modular.mall.service;

import com.dolaing.modular.mall.model.OrderGoods;
import com.baomidou.mybatisplus.service.IService;
import com.dolaing.modular.mall.vo.OrderGoodsVo;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.system.model.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zx
 * @since 2018-08-04
 */
public interface IOrderGoodsService extends IService<OrderGoods> {
    List<OrderGoodsVo> queryOrderGoodsByOrderId(Integer orderId);
}
