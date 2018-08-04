package com.dolaing.modular.mall.model;

import java.util.Date;
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
@TableName("mall_order_record")
public class OrderRecord extends Model<OrderRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 对订单操作日志表
     */
    private Integer id;
    /**
     * 被操作的交易号
     */
    @TableField("order_id")
    private Integer orderId;
    /**
     * 操作人
     */
    @TableField("action_user")
    private String actionUser;
    /**
     * 订单操作0,未确认, 1已确认; 2已取消; 3无效; 4退货
     */
    @TableField("order_status")
    private Integer orderStatus;
    /**
     * 发货状态; 0未发货; 1已发货  2已取消  3备货中
     */
    @TableField("shipping_status")
    private Integer shippingStatus;
    /**
     * 支付状态 0未付款;  1已付款中;  2已付款
     */
    @TableField("pay_status")
    private Integer payStatus;
    private String remarks;
    /**
     * 操作时间
     */
    @TableField("log_time")
    private Date logTime;


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

    public String getActionUser() {
        return actionUser;
    }

    public void setActionUser(String actionUser) {
        this.actionUser = actionUser;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(Integer shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "OrderRecord{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", actionUser=" + actionUser +
        ", orderStatus=" + orderStatus +
        ", shippingStatus=" + shippingStatus +
        ", payStatus=" + payStatus +
        ", remarks=" + remarks +
        ", logTime=" + logTime +
        "}";
    }
}
