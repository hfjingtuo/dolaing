package com.dolaing.modular.mall.vo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.dolaing.core.base.model.BaseModel;
import com.dolaing.core.common.constant.GlobalData;
import com.dolaing.modular.api.enums.OrderStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author zx
 * @since 2018-08-04
 */
@Data
public class OrderInfoVo extends BaseModel<OrderInfoVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号,唯一DLY00000001
     */
    private String orderSn;

    /**
     * 买家用户account
     */
    private String userId;

    private Integer goodsId;//商品ID

    private Integer goodsNum;//商品数量

    /**
     * 订单的状态;0未确认,1确认,2已取消,3无效,4退货
     */
    private Integer orderStatus;

    /**
     * 商品配送情况;0未发货,1已发货,2已收货,4退货
     */
    private Integer shippingStatus;

    /**
     * 支付状态;0 未付款;1 已付款
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
     * 卖家收款状态
     */
    private Integer sellerReceiveStatus;

    /**
     * 卖家已收金额(单位为元)
     */
    private BigDecimal sellerMoneyReceived;

    /**
     * 卖家应收金额(单位为元)
     */
    private BigDecimal sellerReceivableAmount;

    /**
     * 农户收款状态 0 未收款  1 定金收款中 2 定金已到账  3 尾款收款中  4尾款已到账
     */
    private Integer farmerReceiveStatus;

    /**
     * 农户已收金额(单位为元)
     */
    private BigDecimal farmerMoneyReceived;

    /**
     * 农户应收金额(单位为元)
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
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 卖家账号
     */
    private String seller;

    /**
     * 商品明细
     */
    private List<OrderGoodsVo> orderGoodsVos;

    /**
     * 订单完整状态
     */
    private String orderStatusFullName ;
    /**
     * 订单完整状态编号
     */
    private String orderStatusFullCode ;

    /**
     * 农户收款状态 0 未收款  1 定金收款中 2 定金已到账  3 尾款收款中  4尾款已到账
     */
    private String farmerReceiveStatusLabel;

    /**
     * 卖家收款状态 0 未收款  1 定金收款中 2 定金已到账  3 尾款收款中  4尾款已到账
     */
    private String sellerReceiveStatusLabel;

    /**
     * 全部地址
     */
    private String fullAddress;

    public String getOrderStatusFullName() {
        if(this.orderStatus == 0){
           orderStatusFullName = OrderStatusEnum.UN_CONFIRMED.getMessage();
        }else if(this.orderStatus == 1 && this.payStatus == 0){
            orderStatusFullName = OrderStatusEnum.PENDING_PAYMENT.getMessage();
        }else if(this.shippingStatus == 0 && this.payStatus == 1 ){
            orderStatusFullName = OrderStatusEnum.PRODUCTION.getMessage();
        }else if(this.shippingStatus == 1 ){
            orderStatusFullName = OrderStatusEnum.WAIT_FOR_RECEPTION.getMessage();
        }else if(this.shippingStatus == 2 ){
            orderStatusFullName = OrderStatusEnum.COMPLETED.getMessage();
        }
        return orderStatusFullName;
    }

    public String getOrderStatusFullCode() {
        if(this.orderStatus == 0){
            orderStatusFullCode = OrderStatusEnum.UN_CONFIRMED.getCode();
        }else if(this.orderStatus == 1 && this.payStatus == 0){
            orderStatusFullCode = OrderStatusEnum.PENDING_PAYMENT.getCode();
        }else if(this.shippingStatus == 0 && this.payStatus == 1 ){
            orderStatusFullCode = OrderStatusEnum.PRODUCTION.getCode();
        }else if(this.shippingStatus == 1 ){
            orderStatusFullCode = OrderStatusEnum.WAIT_FOR_RECEPTION.getCode();
        }else if(this.shippingStatus == 2 ){
            orderStatusFullCode = OrderStatusEnum.COMPLETED.getCode();
        }
        return orderStatusFullCode;
    }

    public String getFarmerReceiveStatusLabel() {
        if(this.farmerReceiveStatus == 0){
            this.farmerReceiveStatusLabel = "未收款";
        }else if(this.farmerReceiveStatus == 1){
            this.farmerReceiveStatusLabel = "定金收款中";
        }else if(this.farmerReceiveStatus == 2){
            this.farmerReceiveStatusLabel = "定金已到账";
        }else if(this.farmerReceiveStatus == 3){
            this.farmerReceiveStatusLabel = "尾款收款中";
        }else if(this.farmerReceiveStatus == 4){
            this.farmerReceiveStatusLabel = "尾款已到账";
        }
        return farmerReceiveStatusLabel;
    }

    public String getSellerReceiveStatusLabel() {
        if(this.sellerReceiveStatus == 0){
            this.sellerReceiveStatusLabel = "未收款";
        }else if(this.sellerReceiveStatus == 1){
            this.sellerReceiveStatusLabel = "定金收款中";
        }else if(this.sellerReceiveStatus == 2){
            this.sellerReceiveStatusLabel = "定金已到账";
        }else if(this.sellerReceiveStatus == 3){
            this.sellerReceiveStatusLabel = "尾款收款中";
        }else if(this.sellerReceiveStatus == 4){
            this.sellerReceiveStatusLabel = "尾款已到账";
        }
        return sellerReceiveStatusLabel;
    }

    public String getFullAddress() {
        String province = GlobalData.AREAS.get(this.province).getChName();
        String city = GlobalData.AREAS.get(this.city).getChName();
        String area = GlobalData.AREAS.get(this.district).getChName();
        this.fullAddress = province + " " + city + " " + area + " " + this.address;
        return fullAddress;
    }

    @Override
    public String toString() {
        return "OrderInfoVo{" +
                "orderSn='" + orderSn + '\'' +
                ", userId='" + userId + '\'' +
                ", goodsId=" + goodsId +
                ", goodsNum=" + goodsNum +
                ", orderStatus=" + orderStatus +
                ", shippingStatus=" + shippingStatus +
                ", payStatus=" + payStatus +
                ", consignee='" + consignee + '\'' +
                ", country=" + country +
                ", province=" + province +
                ", city=" + city +
                ", district=" + district +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", tel='" + tel + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", postscript='" + postscript + '\'' +
                ", paymentId=" + paymentId +
                ", paymentName='" + paymentName + '\'' +
                ", goodsAmount=" + goodsAmount +
                ", buyerMoneyPaid=" + buyerMoneyPaid +
                ", buyerOrderAmount=" + buyerOrderAmount +
                ", sellerReceiveStatus=" + sellerReceiveStatus +
                ", sellerMoneyReceived=" + sellerMoneyReceived +
                ", sellerReceivableAmount=" + sellerReceivableAmount +
                ", farmerReceiveStatus=" + farmerReceiveStatus +
                ", farmerMoneyReceived=" + farmerMoneyReceived +
                ", farmerReceivableAmount=" + farmerReceivableAmount +
                ", remarks='" + remarks + '\'' +
                ", paidTime=" + paidTime +
                ", deliveredTime=" + deliveredTime +
                ", receivedTime=" + receivedTime +
                ", completedTime=" + completedTime +
                ", shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", seller='" + seller + '\'' +
                ", orderGoodsVos=" + orderGoodsVos +
                ", orderStatusFullName='" + orderStatusFullName + '\'' +
                ", orderStatusFullCode='" + orderStatusFullCode + '\'' +
                ", farmerReceiveStatusLabel='" + farmerReceiveStatusLabel + '\'' +
                ", sellerReceiveStatusLabel='" + sellerReceiveStatusLabel + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                '}';
    }
}
