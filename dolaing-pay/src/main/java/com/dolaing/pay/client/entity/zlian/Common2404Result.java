package com.dolaing.pay.client.entity.zlian;

import lombok.Data;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 8:57 2018/5/18
 * @Modified By:
 */
@Data
public class Common2404Result {
    //证联支付分配给商户的机构代码
    private String instuId ;
    //商户的系统日期，YYYYMMDD（証联发起该报文时可空）
    private String merchantDate ;
    //商户的时间戳，HHMMSS（証联发起该报文时可空）
    private String merchantTime ;
    //商户系统发起交易时的请求流水号（証联发起该报文时可空）
    private String merchantSeqId ;
    //原业务请求的交易日期YYYYMMDD
    private String merchantLiqDate ;
    //业务类型 01：资金转入 02：资金转出
    private String tradeType ;
    //资金转入金额
    private String pnrDate ;
    //证联支付发给基金的时间戳
    private String pnrTime ;
    //证联支付的流水号
    private String pnrSeqId ;
    //应答码
    private String respCode ;
    //业务应答描述
    private String respDesc ;
    //该笔交易清算日期
    private String settleDate ;
    //保留域
    private String resv ;
}
