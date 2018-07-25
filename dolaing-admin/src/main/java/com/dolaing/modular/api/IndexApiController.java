package com.dolaing.modular.api;

import com.dolaing.core.base.controller.BaseController;
import com.dolaing.core.base.tips.SuccessTip;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: zx
 * Date: Created in 2018/07/25 11:44
 * Copyright: Copyright (c) 2018
 * Description： 主页
 */
@RestController
@RequestMapping("/dolaing")
public class IndexApiController extends BaseController {

    /**
     * 首页接口
     */
    @RequestMapping("/index")
    public Object index(){
        return new SuccessTip();
    }
}
