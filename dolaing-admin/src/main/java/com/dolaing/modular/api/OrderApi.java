package com.dolaing.modular.api;

import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.common.constant.JwtConstants;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.mall.model.MallGoods;
import com.dolaing.modular.mall.model.OrderInfo;
import com.dolaing.modular.mall.service.IOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Author: zx
 * Date: Created in 2018/08/3 11:44
 * Copyright: Copyright (c) 2018
 * Description： 订单
 */
@RestController
@RequestMapping("/dolaing")
public class OrderApi extends BaseApi {

    @Autowired
    private IOrderInfoService orderInfoService;

    /**
     * 生成订单
     */
    @PostMapping("/order/generateOrder")
    public Object publish(@RequestBody OrderInfo orderInfo) {
        String requestHeader = getHeader(JwtConstants.AUTH_HEADER);
        String userName = "";
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            userName = JwtTokenUtil.getUsernameFromToken(requestHeader.substring(7));
        }
        if (ToolUtil.isOneEmpty(orderInfo.getConsignee(), orderInfo.getAddress())) {
            return new ErrorTip(500, "订单生成失败，参数有空值");
        }
        orderInfo.setCreateBy(userName);
        orderInfo.setCreateTime(new Date());
        orderInfo.insert();
        return SUCCESS_TIP;
    }
}
