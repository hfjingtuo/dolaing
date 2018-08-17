package com.dolaing.modular.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.common.annotion.AuthAccess;
import com.dolaing.core.common.constant.Const;
import com.dolaing.core.common.constant.GlobalData;
import com.dolaing.core.support.HttpKit;
import com.dolaing.core.util.TokenUtil;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.mall.model.MallGoods;
import com.dolaing.modular.mall.model.OrderGoods;
import com.dolaing.modular.mall.model.OrderInfo;
import com.dolaing.modular.mall.service.IOrderInfoService;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.redis.service.RedisTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    @Autowired
    private RedisTokenService redisTokenService;

    /**
     * 生成订单
     */
    @AuthAccess
    @PostMapping("/order/generateOrder")
    public Object publish(@RequestBody OrderInfoVo orderInfoVo) {
        String token = TokenUtil.getToken(HttpKit.getRequest());
        String account = redisTokenService.getTokenModel(token).getAccount();
        if (ToolUtil.isOneEmpty(orderInfoVo.getGoodsId(), orderInfoVo.getMobile(), orderInfoVo.getConsignee(), orderInfoVo.getAddress())) {
            return new ErrorTip(500, "订单生成异常，请稍候重试");
        }
        Integer goodsId = orderInfoVo.getGoodsId();
        MallGoods mallGoods = new MallGoods().selectById(goodsId);

        String orderSn = getOrderSn();
        BigDecimal goodsAmount = orderInfoVo.getBuyerOrderAmount();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderSn(orderSn);
        orderInfo.setConsignee(orderInfoVo.getConsignee());
        orderInfo.setMobile(orderInfoVo.getMobile());
        orderInfo.setCountry(Const.CHINA_ID);
        orderInfo.setProvince(orderInfoVo.getProvince());
        orderInfo.setCity(orderInfoVo.getCity());
        orderInfo.setDistrict(orderInfoVo.getDistrict());
        orderInfo.setAddress(orderInfoVo.getAddress());
        orderInfo.setRemarks(orderInfoVo.getRemarks());
        orderInfo.setGoodsAmount(goodsAmount);
        orderInfo.setBuyerOrderAmount(goodsAmount);
        orderInfo.setOrderStatus(1);
        orderInfo.setShippingStatus(0);
        orderInfo.setPayStatus(0);//付款状态：未付款
        orderInfo.setPaymentId(0);//支付方式：证联支付
        orderInfo.setSellerReceiveStatus(0);//收款状态：未收款
        orderInfo.setFarmerReceiveStatus(0);//收款状态：未收款
        orderInfo.setSellerMoneyReceived(BigDecimal.ZERO);
        //卖家应收金额 = 商品总额 * 10%
        orderInfo.setSellerReceivableAmount(goodsAmount.multiply(Const.SELLERRECEIVABLEAMOUNT_RATE));
        orderInfo.setBuyerMoneyPaid(BigDecimal.ZERO);
        orderInfo.setFarmerMoneyReceived(BigDecimal.ZERO);
        //农户应收金额 = 商品总额 * 80%
        orderInfo.setFarmerReceivableAmount(goodsAmount.multiply(Const.FARMERRECEIVABLEAMOUNT_RATE));
        orderInfo.setShopId(mallGoods.getShopId());
        orderInfo.setUserId(account);
        orderInfo.setCreateBy(account);
        orderInfo.setCreateTime(new Date());
        orderInfo.setShopId(orderInfoVo.getShopId());
        orderInfoService.saveOrderInfo(orderInfo);
        Integer orderId = orderInfo.getId();

        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setOrderId(orderId);
        orderGoods.setGoodsSn(mallGoods.getGoodsSn());
        orderGoods.setGoodsName(mallGoods.getGoodsName());
        orderGoods.setGoodsPrice(mallGoods.getShopPrice());
        orderGoods.setGoodsId(goodsId);
        orderGoods.setGoodsNumber(orderInfoVo.getGoodsNum());
        orderGoods.insert();

        /*******下单 新的库存=库存-订单商品数量******/
        mallGoods.setGoodsNumber(mallGoods.getGoodsNumber() - orderInfoVo.getGoodsNum());
        mallGoods.updateById();
        return render(orderId);
    }

    /**
     * 订单详情：确认状态下没有付款
     */
    @AuthAccess
    @PostMapping("/order/detail")
    public Object detail(@RequestParam String orderId) {
        OrderInfo orderInfo = new OrderInfo().selectById(orderId);
        String token = TokenUtil.getToken(HttpKit.getRequest());
        String account = redisTokenService.getTokenModel(token).getAccount();
        if (orderInfo != null) {
            if (!account.equals(orderInfo.getUserId())) {
                return new ErrorTip(500, "您没有权限查看该订单");
            }
            if (orderInfo.getOrderStatus() == 1 && orderInfo.getPayStatus() == 1) {
                return new ErrorTip(500, "该订单已完成支付");
            }

            if (orderInfo.getOrderStatus() == 1 && orderInfo.getPayStatus() == 0) {
                Integer province = orderInfo.getProvince();
                Integer city = orderInfo.getCity();
                Integer district = orderInfo.getDistrict();
                if (province != null && city != null && district != null) {
                    orderInfo.setAddress(GlobalData.AREAS.get(province).getChName() + GlobalData.AREAS.get(city).getChName() + GlobalData.AREAS.get(district).getChName() + orderInfo.getAddress());
                }
                return render(orderInfo);
            } else {
                return new ErrorTip(500, "订单状态异常");
            }
        }
        return new ErrorTip(500, "订单不存在");
    }

    /**
     * 订单详情：确认状态下已付款
     */
    @AuthAccess
    @PostMapping("/order/detailPaied")
    public Object detailPaied(@RequestParam String orderId) {
        OrderInfo orderInfo = new OrderInfo().selectById(orderId);
        String token = TokenUtil.getToken(HttpKit.getRequest());
        String account = redisTokenService.getTokenModel(token).getAccount();
        if (orderInfo != null) {
            if (!account.equals(orderInfo.getUserId())) {
                return new ErrorTip(500, "您没有权限查看该订单");
            }
            if (orderInfo.getOrderStatus() == 1 && orderInfo.getPayStatus() == 0) {
                return new ErrorTip(500, "该订单待付款");
            }
            if (orderInfo.getOrderStatus() == 1 && orderInfo.getPayStatus() == 1) {
                Integer province = orderInfo.getProvince();
                Integer city = orderInfo.getCity();
                Integer district = orderInfo.getDistrict();
                if (province != null && city != null && district != null) {
                    orderInfo.setAddress(GlobalData.AREAS.get(province).getChName() + GlobalData.AREAS.get(city).getChName() + GlobalData.AREAS.get(district).getChName() + orderInfo.getAddress());
                }
                return render(orderInfo);
            } else {
                return new ErrorTip(500, "订单状态异常");
            }
        }
        return new ErrorTip(500, "订单不存在");
    }

    /**
     * 生成订单号
     *
     * @return orderSn
     */
    public String getOrderSn() {
        String orderSn;
        String maxOrderSn;
        OrderInfo orderInfo;
        Wrapper<OrderInfo> wrapper = new EntityWrapper<>();
        wrapper.orderBy("order_sn", false);
        Page<OrderInfo> page = new Page<>(1, 1);
        Page<OrderInfo> orders = orderInfoService.selectPage(page, wrapper);
        if (orders != null && orders.getRecords() != null && orders.getRecords().size() > 0) {
            orderInfo = orders.getRecords().get(0);
            maxOrderSn = orderInfo.getOrderSn().substring(3, orderInfo.getOrderSn().length());
            Integer temp = Integer.valueOf(maxOrderSn);
            orderSn = "DLY" + String.format("%08d", temp + 1);
        } else {
            orderSn = "DLY00000001";
        }
        System.out.println("orderSn=" + orderSn);
        return orderSn;
    }
}
