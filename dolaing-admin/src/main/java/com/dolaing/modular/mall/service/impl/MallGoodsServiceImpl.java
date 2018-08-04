package com.dolaing.modular.mall.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dolaing.core.datascope.DataScope;
import com.dolaing.modular.mall.dao.MallGoodsMapper;
import com.dolaing.modular.mall.model.MallGoods;
import com.dolaing.modular.mall.service.MallGoodsService;
import com.dolaing.modular.mall.vo.MallGoodsVo;
import com.dolaing.modular.system.dao.UserMapper;
import com.dolaing.modular.system.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zx
 * @since 2018-08-25
 */
@Service
public class MallGoodsServiceImpl extends ServiceImpl<MallGoodsMapper, MallGoods> implements MallGoodsService {

    @Override
    public List<MallGoodsVo> getGoodsList(Pagination page, String createBy) {
        return this.baseMapper.getGoodsList(page, createBy);
    }
}
