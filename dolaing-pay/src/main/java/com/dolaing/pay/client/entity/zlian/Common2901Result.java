package com.dolaing.pay.client.entity.zlian;

import lombok.Data;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 8:54 2018/5/18
 * @Modified By:
 */
@Data
public class Common2901Result {
    //证联支付分配给商户的机构代码
    private String instuId ;
    //商户的系统日期，YYYYMMDD（証联发起该报文时可空）
    private String merchantDate ;
    //商户的时间戳，HHMMSS（証联发起该报文时可空）
    private String merchantTime ;
    //商户系统发起交易时的请求流水号（証联发起该报文时可空）
    private String merchantSeqId ;
    //证联支付发起交易的系统日期，YYYYMMDD（商户发起该报文时可空）
    private String pnrDate ;
    //证联支付发给基金的时间戳，HHMMSS（商户发起该报文时可空）
    private String pnrTime ;
    //证联支付的流水号。需要保证流水号的唯一性（商户发起该报文时可空）
    private String pnrSeqId ;
    //应答码
    private String respCode ;
    //业务应答描述
    private String respDesc ;
    //保留域
    private String resv ;
    //备注1
    private String rem1 ;
    //备注2
    private String rem2 ;

}
