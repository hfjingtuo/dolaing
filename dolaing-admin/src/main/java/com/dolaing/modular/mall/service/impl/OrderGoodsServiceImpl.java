package com.dolaing.modular.mall.service.impl;

import com.dolaing.modular.mall.dao.OrderInfoMapper;
import com.dolaing.modular.mall.model.OrderGoods;
import com.dolaing.modular.mall.dao.OrderGoodsMapper;
import com.dolaing.modular.mall.service.IOrderGoodsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dolaing.modular.mall.vo.OrderGoodsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zx
 * @since 2018-08-04
 */
@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements IOrderGoodsService {
    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    @Override
    public List<OrderGoodsVo> queryOrderGoodsByOrderId(Integer orderId) {
        return orderGoodsMapper.queryOrderGoodsByOrderId(orderId);
    }



}
