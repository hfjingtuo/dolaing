package com.dolaing.pay.client.entity.zlian;

import lombok.Data;

/**
 * @Author:张立华
 * @Description:同步开户 108
 * @Date：Created in 18:13 2018/5/11
 * @Modified By:
 */
@Data
public class MarginRegisterDTO{
    private static final long serialVersionUID = 1L;
    // 证联支付分配给商户的机构代码 最大长度9位 不可为空
    private String instuId ;
    // 商户的系统日期，YYYYMMDD 最大长度8位 不可为空
    private String fundDate ;
    // 商户的时间戳，HHMMSS 最大长度6位 不可为空
    private String fundTime ;
    // 商户系统流水号。需要保证一个交易日中流水号的唯一性 最大长度32位 不可为空
    private String fundSeqId ;
    // 柜台号 最大长度3位 不可为空
    private String counterNo ;
    // 客户类型：0：个人 1：企业 最大长度1位 不可为空
    private String custType ;
    // 客户的名称 最大长度120位 不可为空
    private String userNameText ;
    // 证件类型，参考附录 最大长度2位 不可为空
    private String certType ;
    // 证件号码 最大长度32位 不可为空
    private String certId ;
    // 组织机构代码 注：（企业客户开户不允许为空，个人客户可空）  最大长度32位 可空
    private String orgId ;
    // 银行代码 最大长度4位 不可为空
    private String bankCode ;
    // 省份代码 最大长度4位 可为空
    private String bankProvinceCode ;
    // 地区代码 最大长度4位 可为空
    private String bankRegionCode ;
    // 银行卡号 最大长度32位 不可为空
    private String cardNo ;
    // 支付密码（银行代码为0001时不允许为空，其他可空） 最大长度32位 可为空
    private String payPassWord ;
    // 手机号 最大长度11位 不可为空
    private String mobile ;
    // 手机验证码 最大长度11位 不可为空
    private String identifyingCode ;
    // 保留域，接收方必须原样回送该字段数据 最大长度128位 可为空
    private String resv ;


}
