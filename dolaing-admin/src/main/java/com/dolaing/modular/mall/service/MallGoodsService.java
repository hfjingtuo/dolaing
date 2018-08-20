package com.dolaing.modular.mall.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.dolaing.modular.mall.model.MallGoods;

/**
 * @author zx
 * @since 2018-08-25
 */
public interface MallGoodsService extends IService<MallGoods> {

    /**
     * 查询未删除已上架的商品
     */
    Page getGoodsList(Page page , String createBy);

    /**
     * 查询未删除已上架且在认购期的所有商品、排除当前商品(用于展示在商品详情页)
     */
    Page getAllGoods(Page page , String goodsId);

    /**
     * 批量删除商品
     * @param account
     * @param ids
     * @return
     */
    Boolean batchDelete(String account , String ids);

}
