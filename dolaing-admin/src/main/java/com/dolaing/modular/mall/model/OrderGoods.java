package com.dolaing.modular.mall.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zx
 * @since 2018-08-04
 */
@TableName("mall_order_goods")
public class OrderGoods extends Model<OrderGoods> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单商品信息id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单号
     */
    @TableField("order_id")
    private Integer orderId;
    /**
     * 商品的的id
     */
    @TableField("goods_id")
    private Integer goodsId;
    /**
     * 商品的名称
     */
    @TableField("goods_name")
    private String goodsName;
    /**
     * 商品的唯一货号
     */
    @TableField("goods_sn")
    private String goodsSn;
    /**
     * 商品的购买数量
     */
    @TableField("goods_number")
    private Integer goodsNumber;
    /**
     * 商品的售价
     */
    @TableField("goods_price")
    private BigDecimal goodsPrice;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

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
