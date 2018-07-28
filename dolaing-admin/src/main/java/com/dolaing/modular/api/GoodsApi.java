package com.dolaing.modular.api;

import com.dolaing.core.base.controller.BaseController;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.modular.mall.model.MallGoods;
import com.dolaing.modular.mall.model.MallShop;
import com.dolaing.modular.mall.service.MallGoodsService;
import com.dolaing.modular.mall.vo.MallGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Author: zx
 * Date: Created in 2018/07/25 11:44
 * Copyright: Copyright (c) 2018
 * Description： 商品控制器
 */
@RestController
@RequestMapping("/dolaing")
public class GoodsApi extends BaseController {

    /**
     * 商品详情
     */
    @GetMapping("/goods/detail/{goodsId}")
    public Object detail(@PathVariable Integer goodsId) {
        HashMap<String, Object> result = new HashMap<>();
        MallGoods mallGoods = new MallGoods().selectById(goodsId);
        if (mallGoods != null) {
            MallShop mallShop = new MallShop().selectById(mallGoods.getShopId());
            result.put("mallGoods", mallGoods);
            result.put("mallShop", mallShop);
            return result;
        }
        return new ErrorTip(500, "商品不存在");
    }
}
