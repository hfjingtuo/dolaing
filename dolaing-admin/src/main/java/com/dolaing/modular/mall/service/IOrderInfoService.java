package com.dolaing.modular.mall.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.modular.mall.model.OrderInfo;
import com.baomidou.mybatisplus.service.IService;
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
public interface IOrderInfoService extends IService<OrderInfo> {
    Page queryOrdersByUser(Page page , User user);

    Integer saveOrderInfo(OrderInfo orderInfo);
}
