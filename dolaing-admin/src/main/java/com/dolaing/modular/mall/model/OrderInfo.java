package com.dolaing.modular.mall.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.dolaing.core.base.model.BaseModel;
import io.swagger.models.auth.In;
import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author zx
 * @since 2018-08-04
 */
@Data
@TableName("mall_order_info")
public class OrderInfo extends BaseModel<OrderInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号,唯一DLY00000001
     */
    private String orderSn;

    /**
     * 买家用户account
     */
    private String userId;

    /**
     * 订单的状态;0未确认,1确认,2已取消,3无效,4退货
     */
    private Integer orderStatus;

    /**
     * 商品配送情况;0未发货,1已发货,2已收货,4退货
     */
    private Integer shippingStatus;

    /**
     * 支付状态;0未付款;1已付款
     */
    private Integer payStatus;

    /**
     * 收货人的姓名
     */
    private String consignee;

    /**
     * 收货人的国家
     */
    private Integer country;

    /**
     * 收货人的省份
     */
    private Integer province;

    /**
     * 收货人的城市
     */
    private Integer city;

    /**
     * 收货人的城市
     */
    private Integer district;

    /**
     * 收货人的详细地址
     */
    private String address;

    /**
     * 收货人的邮编
     */
    private String zipcode;

    /**
     * 收货人的电话
     */
    private String tel;

    /**
     * 收货人的手机
     */
    private String mobile;

    /**
     * 收货人的邮箱
     */
    private String email;

    /**
     * 订单附言,由用户提交订单前填写
     */
    private String postscript;

    /**
     * 支付方式
     */
    private Integer paymentId;

    /**
     * 用户选择的支付方式名称
     */
    private String paymentName;

    /**
     * 商品的总金额(单位为元)
     */
    private BigDecimal goodsAmount;

    /**
     * 买家已付款金额(单位为元)
     */
    private BigDecimal buyerMoneyPaid;

    /**
     * 买家应付款金额(单位为元)
     */
    private BigDecimal buyerOrderAmount;

    /**
     * 卖家收款状态0 未收款  1 定金收款中 2 定金已到账  3 尾款收款中  4尾款已到账
     */
    private Integer sellerReceiveStatus;

    /**
     * 卖家已收金额(单位为元)
     */
    private BigDecimal sellerMoneyReceived;

    /**
     * 卖家应收金额(单位为元)     10%
     */
    private BigDecimal sellerReceivableAmount;

    /**
     * 农户收款状态
     */
    private Integer farmerReceiveStatus;

    /**
     * 农户已收金额(单位为元)
     */
    private BigDecimal farmerMoneyReceived;

    /**
     * 农户应收金额(单位为元)     80%
     */
    private BigDecimal farmerReceivableAmount;

    /**
     * 备注(卖家)
     */
    private String remarks;

    /**
     * 付款时间
     */
    private Date paidTime;

    /**
     * 发货时间
     */
    private Date deliveredTime;

    /**
     * 收货时间
     */
    private Date receivedTime;

    /**
     * 完成时间
     */
    private Date completedTime;

    /**
     * 店铺id
     */
    private Integer shopId;

    public OrderInfo() {
    }

    public OrderInfo(Integer id) {
        this.id = id;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
