package com.dolaing.modular.mall.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.dolaing.core.base.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author zx
 * @since 2018-08-04
 */
@Data
@TableName("mall_order_goods")
public class OrderGoods extends BaseModel<OrderGoods> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private Integer orderId;

    /**
     * 商品的的id
     */
    private Integer goodsId;

    /**
     * 商品的名称
     */
    private String goodsName;

    /**
     * 商品的唯一货号
     */
    private String goodsSn;

    /**
     * 商品的购买数量
     */
    private Integer goodsNumber;

    /**
     * 商品的售价
     */
    private BigDecimal goodsPrice;

    @Override
    public String toString() {
        return "OrderGoods{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", goodsId=" + goodsId +
                ", goodsName=" + goodsName +
                ", goodsSn=" + goodsSn +
                ", goodsNumber=" + goodsNumber +
                ", goodsPrice=" + goodsPrice +
                "}";
    }
}
