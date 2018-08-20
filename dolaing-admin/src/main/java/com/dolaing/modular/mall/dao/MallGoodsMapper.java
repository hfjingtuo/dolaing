package com.dolaing.modular.mall.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dolaing.modular.mall.model.MallGoods;
import com.dolaing.modular.mall.vo.MallGoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zx
 * @since 2018-07-25
 */
public interface MallGoodsMapper extends BaseMapper<MallGoods> {

    /**
     * 查询未删除已上架的商品
     */
    List<MallGoodsVo> getGoodsList(Map map);

    /**
     * 查询未删除已上架且在认购期的所有商品、排除当前商品(用于展示在商品详情页)
     */
    List<MallGoodsVo> getAllGoods(Map map);

    Integer queryGoodsCountByAccount(@Param("createBy") String createBy);

    /**
     * 批量删除商品
     * @param map
     * @return
     */
    Boolean batchDelete(Map map);

}