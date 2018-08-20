package com.dolaing.modular.mall.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dolaing.modular.mall.dao.MallGoodsMapper;
import com.dolaing.modular.mall.model.MallGoods;
import com.dolaing.modular.mall.service.MallGoodsService;
import com.dolaing.modular.mall.vo.MallGoodsVo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
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

    @Resource
    private MallGoodsMapper mallGoodsMapper;

    @Override
    public Page getGoodsList(Page page, String createBy) {
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
    public Page getAllGoods(Page page, String goodsId) {
        Map map = new HashMap();
        map.put("page", page);
        map.put("id", goodsId);
        List<MallGoodsVo> mallGoodsVos = mallGoodsMapper.getAllGoods(map);
        page.setTotal(mallGoodsVos.size());
        page.setRecords(mallGoodsVos);
        return page;
    }
    @Override
    public Boolean batchDelete(String account, String ids) {
        Map map = new HashMap();
        map.put("account",account);
        map.put("ids",ids);
        return mallGoodsMapper.batchDelete(map);
    }
}
