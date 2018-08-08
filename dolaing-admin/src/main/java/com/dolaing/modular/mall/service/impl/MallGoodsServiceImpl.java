package com.dolaing.modular.mall.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dolaing.core.datascope.DataScope;
import com.dolaing.modular.mall.dao.MallGoodsMapper;
import com.dolaing.modular.mall.model.MallGoods;
import com.dolaing.modular.mall.service.MallGoodsService;
import com.dolaing.modular.mall.vo.MallGoodsVo;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.system.dao.UserMapper;
import com.dolaing.modular.system.model.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zx
 * @since 2018-08-25
 */
@Service
public class MallGoodsServiceImpl extends ServiceImpl<MallGoodsMapper, MallGoods> implements MallGoodsService {

    @Override
    public Page getGoodsList(Page page,String createBy) {
        Map map = new HashMap();
        map.put("page", page);
        map.put("createBy", createBy);
        Integer count = this.baseMapper.queryGoodsCountByAccount(createBy);
        if (count <= 0) {
            page.setRecords(Collections.emptyList());
        }
        List<MallGoodsVo> mallGoodsVos = this.baseMapper.getGoodsList(map);
        page.setTotal(count);
        page.setRecords(mallGoodsVos);
        return page;
    }

    @Override
    public List<MallGoodsVo> getGoodsList2(Pagination page, String createBy) {
        return this.baseMapper.getGoodsList2(page,createBy);
    }
}
