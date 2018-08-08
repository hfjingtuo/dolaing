package com.dolaing.modular.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付流水表vo类
 *
 * @Author 王柳
 * @Date 2018/8/7 18:17
 */
@Data
public class UserAccountRecordVo {

    /**
     * id
     */
    private Integer id;

    /**
     * 资金的数目，正数为增加，负数为减少
     */
    private BigDecimal amount;

    /**
     * 支付渠道 0 证联支付
     */
    private Integer paymentId;

    /**
     * 创建时间（付款时间）
     */
    private Date createTime;

    /**
     * 更新时间（到账时间）
     */
    private Date updateTime;

    /**
     * 证联交易号
     */
    private String seqId;

    /**
     * 支付方式名称： 0 证联支付
     */
    private String paymentName;

    public String getPaymentName() {
        if (this.paymentId == 0){
            paymentName = "证联支付";
        }else {
            paymentName = "";
        }
        return paymentName;
    }
}
