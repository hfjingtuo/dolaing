package com.dolaing.pay.client.entity.zlian;

import lombok.Data;
/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 8:57 2018/5/18
 * @Modified By:
 */
@Data
public class Common208Result {
    //证联支付分配给商户的机构代码
    private String instuId ;
    //商户的系统日期，YYYYMMDD（証联发起该报文时可空）
    private String fundDate ;
    //商户的时间戳，HHMMSS（証联发起该报文时可空）
    private String fundTime ;
    //商户系统发起交易时的请求流水号（証联发起该报文时可空）
    private String fundSeqId ;
    //用户在证联支付平台里的客户号
    private String userId ;
    //柜台号
    private String counterNo ;
    //客户的姓名
    private String userNameText ;
    //证件类型
    private String certType ;
    //证件号码
    private String certId ;
    //银行代码
    private String bankCode ;
    //省份代码
    private String bankProvinceCode ;
    //地区代码
    private String bankRegionCode ;
    //银行卡号
    private String cardNo ;
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
    //保留域
    private String resv ;
}
