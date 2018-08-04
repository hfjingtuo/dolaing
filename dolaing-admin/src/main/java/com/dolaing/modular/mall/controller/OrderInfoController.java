package com.dolaing.modular.mall.controller;

import com.dolaing.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.dolaing.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.dolaing.modular.mall.model.OrderInfo;
import com.dolaing.modular.mall.service.IOrderInfoService;

/**
 * 订单控制器
 *
 * @author zx
 * @Date 2018-08-04 14:07:10
 */
@Controller
@RequestMapping("/orderInfo")
public class OrderInfoController extends BaseController {

    private String PREFIX = "/mall/orderInfo/";

    @Autowired
    private IOrderInfoService orderInfoService;

    /**
     * 跳转到订单首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "orderInfo.html";
    }

    /**
     * 跳转到添加订单
     */
    @RequestMapping("/orderInfo_add")
    public String orderInfoAdd() {
        return PREFIX + "orderInfo_add.html";
    }

    /**
     * 跳转到修改订单
     */
    @RequestMapping("/orderInfo_update/{orderInfoId}")
    public String orderInfoUpdate(@PathVariable Integer orderInfoId, Model model) {
        OrderInfo orderInfo = orderInfoService.selectById(orderInfoId);
        model.addAttribute("item",orderInfo);
        LogObjectHolder.me().set(orderInfo);
        return PREFIX + "orderInfo_edit.html";
    }

    /**
     * 获取订单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return orderInfoService.selectList(null);
    }

    /**
     * 新增订单
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(OrderInfo orderInfo) {
        orderInfoService.insert(orderInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除订单
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer orderInfoId) {
        orderInfoService.deleteById(orderInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改订单
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(OrderInfo orderInfo) {
        orderInfoService.updateById(orderInfo);
        return SUCCESS_TIP;
    }

    /**
     * 订单详情
     */
    @RequestMapping(value = "/detail/{orderInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("orderInfoId") Integer orderInfoId) {
        return orderInfoService.selectById(orderInfoId);
    }
}
