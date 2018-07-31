package com.dolaing.modular.api;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.mall.service.MallGoodsService;
import com.dolaing.modular.mall.vo.MallGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 首页接口
     */
    @PostMapping("/index")
    public Map index(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        Map<String, Object> map = new HashMap<>();
        Pagination page = new Pagination(pageNo, pageSize);
        List<MallGoodsVo> list = mallGoodsService.getGoodsList(page);
        map.put("list",list);
        System.out.println(map);
        return map;
    }
}
