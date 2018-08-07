package com.dolaing.modular.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.base.tips.SuccessTip;
import com.dolaing.core.common.constant.Const;
import com.dolaing.core.common.constant.GlobalData;
import com.dolaing.core.support.HttpKit;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.mall.model.MallGoods;
import com.dolaing.modular.mall.model.OrderGoods;
import com.dolaing.modular.mall.model.OrderInfo;
import com.dolaing.modular.mall.service.IOrderInfoService;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.system.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    @Autowired
    private IAreaService areaService;

    /**
     * 生成订单
     */
    @PostMapping("/order/generateOrder")
    public Object publish(@RequestBody OrderInfoVo orderInfoVo) {
        String token = JwtTokenUtil.getToken(HttpKit.getRequest());
        String account = JwtTokenUtil.getAccountFromToken(token);
        if (ToolUtil.isOneEmpty(orderInfoVo.getGoodsId(), orderInfoVo.getMobile(), orderInfoVo.getConsignee(), orderInfoVo.getAddress())) {
            return new ErrorTip(500, "订单生成失败，参数有空值");
        }
        Integer goodsId = orderInfoVo.getGoodsId();
        MallGoods mallGoods = new MallGoods().selectById(goodsId);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderSn(getOrderSn());
        orderInfo.setConsignee(orderInfoVo.getConsignee());
        orderInfo.setMobile(orderInfoVo.getMobile());
        orderInfo.setCountry(Const.CHINA_ID);
        orderInfo.setProvince(orderInfoVo.getProvince());
        orderInfo.setCity(orderInfoVo.getCity());
        orderInfo.setDistrict(orderInfoVo.getDistrict());
        orderInfo.setAddress(orderInfoVo.getAddress());
        orderInfo.setRemarks(orderInfoVo.getRemarks());
        orderInfo.setBuyerOrderAmount(orderInfoVo.getBuyerOrderAmount());
        orderInfo.setOrderStatus(1);
        orderInfo.setShippingStatus(0);
        orderInfo.setPayStatus(0);
        orderInfo.setShopId(mallGoods.getShopId());
        orderInfo.setUserId(account);
        orderInfo.setCreateBy(account);
        orderInfo.setCreateTime(new Date());
        orderInfoService.saveOrderInfo(orderInfo);
        Integer orderId = orderInfo.getId();

        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setOrderId(orderId);
        orderGoods.setGoodsName(mallGoods.getGoodsName());
        orderGoods.setGoodsSn(goodsId + "");
        orderGoods.setGoodsPrice(mallGoods.getShopPrice());
        orderGoods.setGoodsId(goodsId);
        orderGoods.setGoodsNumber(orderInfoVo.getGoodsNum());
        orderGoods.insert();
        return new SuccessTip(200, orderId + "");
    }

    /**
     * 订单详情
     */
    @GetMapping("/order/detail/{orderId}")
    public Object detail(@PathVariable Integer orderId) {
        HashMap<String, Object> result = new HashMap<>();
        OrderInfo orderInfo = orderInfoService.selectById(orderId);
        if (orderInfo != null) {
            String province = GlobalData.AREAS.get(orderInfo.getProvince()).getChName();
            String city = GlobalData.AREAS.get(orderInfo.getCity()).getChName();
            String area = GlobalData.AREAS.get(orderInfo.getDistrict()).getChName();
            orderInfo.setAddress(province + city + area + orderInfo.getAddress());
            result.put("orderInfo", orderInfo);
            return result;
        }
        return new ErrorTip(500, "订单不存在");
    }

    /**
     * 生成订单号
     *
     * @return orderSn
     */
    public static String getOrderSn() {
        String orderSn;
        String maxOrderSn = "DLY99999999";
        Wrapper<OrderInfo> wrapper = new EntityWrapper<>();
        wrapper.orderBy("order_sn", false);
        List<OrderInfo> list = new OrderInfo().selectList(wrapper);
        if (!list.isEmpty() && list.size() != 0) {
            maxOrderSn = list.get(0).getOrderSn();
        }
        maxOrderSn = maxOrderSn.substring(3, 11);
        Integer temp = Integer.valueOf(maxOrderSn);
        orderSn = "DLY" + String.format("%08d", temp + 1);
        return orderSn;
    }
}
