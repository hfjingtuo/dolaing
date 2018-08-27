package com.dolaing.modular.api;

import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.system.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: zx
 * Date: Created in 2018/08/3 11:44
 * Copyright: Copyright (c) 2018
 * Description： 区域选择
 */
@RestController
@RequestMapping("/dolaing")
public class AreaApi extends BaseApi {

    @Autowired
    private IAreaService areaService;

    /**
     * 根据parentId查找下级区域
     */
    @GetMapping("/changeArea/{parentId}")
    public Object getAreaList(@PathVariable String parentId) {
        if (parentId == null) {
            System.out.println(parentId);
        }
        return areaService.findByParentId(parentId);
    }
}
