package com.dolaing.modular.api;

import com.dolaing.core.base.controller.BaseController;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.common.constant.JwtConstants;
import com.dolaing.core.common.exception.BizExceptionEnum;
import com.dolaing.core.exception.DolaingException;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.mall.model.MallGoods;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Author: zx
 * Date: Created in 2018/07/27 11:44
 * Copyright: Copyright (c) 2018
 * Description： 卖家发布商品控制器
 */
@RestController
@RequestMapping("/dolaing/seller")
public class PublishGoodsApi extends BaseController {

    /**
     * 发布商品
     */
    @ResponseBody
    @PostMapping("/publishGoods")
    public Object detail(@RequestBody MallGoods mallGoods) {
        String requestHeader = getHeader(JwtConstants.AUTH_HEADER);
        String userName = "";
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            userName = JwtTokenUtil.getUsernameFromToken(requestHeader.substring(7));
        }
        if (ToolUtil.isOneEmpty(mallGoods.getGoodsName(), mallGoods.getShopPrice())) {
            return new ErrorTip(500, "产品发布失败，参数有空值");
        }
        mallGoods.setCreateBy(userName);
        mallGoods.setCreateTime(new Date());
        mallGoods.insert();
        return SUCCESS_TIP;
    }
}
