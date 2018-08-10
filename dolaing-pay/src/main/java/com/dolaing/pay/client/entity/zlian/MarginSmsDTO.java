package com.dolaing.pay.client.entity.zlian;

import lombok.Data;


/**
 * @Author:张立华
 * @Description:获取短信验证码 2305
 * @Date：Created in 20:02 2018/5/11
 * @Modified By:
 */
@Data
public class MarginSmsDTO {
    private static final long serialVersionUID = 1L;
    // 证联支付分配给商户的机构代码 最大长度9位 不可为空
    private String instuId ;
    // 商户的系统日期，YYYYMMDD 最大长度8位 不可为空
    private String merchantDate ;
    // 证联支付分配给商户的机构代码 最大长度6位 不可为空
    private String merchantTime ;
    // 交易流水必须保证唯一性 最大长度32位 不可为空
    private String merchantSeqId ;
    // 短信验证业务类型 最大长度2位 不可为空
    private String tradeType ;
    // 用户在证联支付平台里的客户号 最大长度20位 可为空
    private String userId ;
    // 客户的姓名 最大长度120位 不可为空
    private String userNameText ;
    // 客户手机号 最大长度32位 不可为空
    private String mobile ;
    // 证件类型 最大长度2位 不可为空
    private String certType ;
    // 证件号码 最大长度25位 不可为空
    private String certId ;
    // 银行代码 最大长度4位 不可为空
    private String bankCode ;
    // 银行卡号 最大长度32位 不可为空
    private String cardNo ;
    // 金额 最大长度12位 不可为空
    private String amt ;
    // 保留域 最大长度128位 不可为空
    private String resv ;

//    public String toDtoString(){
//        return super.toDtoString(this);
//    }

}
