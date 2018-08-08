package com.dolaing.modular.mall.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
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
     * 查询已上架且在认购期的所有商品
     */
    List<MallGoodsVo> getGoodsList(Map map);

    List<MallGoodsVo> getGoodsList2(Pagination page, @Param("createBy")String createBy);

    Integer queryGoodsCountByAccount(@Param("createBy") String createBy);
}