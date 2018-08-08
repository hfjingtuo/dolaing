package com.dolaing.modular.api.seller;

import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.api.base.Result;
import com.dolaing.modular.mall.service.IOrderGoodsService;
import com.dolaing.modular.mall.service.IOrderInfoService;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 卖家订单详情
 *
 * @Author 王柳
 * @Date 2018/8/7 12:18
 */
@RestController
@RequestMapping("/dolaing/sellerOrderDetail")
public class SellerOrderDetailApi extends BaseApi {

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private IOrderGoodsService orderGoodsService;

    @ApiOperation(value = "订单详情")
    @GetMapping("/getOrder")
    public Object getOrder(@RequestParam Integer orderId){
        HashMap<String, Object> result = new HashMap<>();
        OrderInfoVo orderInfoVo = orderInfoService.queryOrderById(orderId);
        if (orderInfoVo != null){
            orderInfoVo.setOrderGoodsVos(orderGoodsService.queryOrderGoodsByOrderId(orderId));
            result.put("orderInfoVo",orderInfoVo);
            return result;
        }
        return new ErrorTip(500, "订单不存在");
    }

}
