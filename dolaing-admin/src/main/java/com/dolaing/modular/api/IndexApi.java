package com.dolaing.modular.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.mall.service.MallGoodsService;
import com.dolaing.modular.mall.vo.MallGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: zx
 * Date: Created in 2018/07/25 11:44
 * Copyright: Copyright (c) 2018
 * Description： 主页
 */
@RestController
@RequestMapping("/dolaing")
public class IndexApi extends BaseApi {

    @Autowired
    private MallGoodsService mallGoodsService;

    /**
     * 查询未删除已上架且在认购期的所有商品(首页商品)、
     * 排除当前商品(商品详情页左侧商品)
     */
    @PostMapping("/getAllGoods")
    public Object index(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String goodsId) {
        Page<MallGoodsVo> page = new Page(pageNo, pageSize);
        page = mallGoodsService.getAllGoods(page, goodsId);
        return render(page);
    }
}
