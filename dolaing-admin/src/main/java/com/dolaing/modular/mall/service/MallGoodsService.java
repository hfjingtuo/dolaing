package com.dolaing.modular.mall.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import com.dolaing.core.datascope.DataScope;
import com.dolaing.modular.mall.model.MallGoods;
import com.dolaing.modular.mall.vo.MallGoodsVo;
import com.dolaing.modular.system.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zx
 * @since 2018-08-25
 */
public interface MallGoodsService extends IService<MallGoods> {

    /**
     * 查询已上架且在认购期的所有商品
     */
    Page getGoodsList(Page page , String createBy);

    List<MallGoodsVo> getAllGoods(Page page);

    Boolean batchDelete(String account , String ids);

}
