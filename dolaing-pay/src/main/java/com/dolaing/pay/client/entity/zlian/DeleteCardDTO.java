package com.dolaing.pay.client.entity.zlian;

import lombok.Data;

/**
 * @Author:zx
 * @Description:取消银行卡 103
 * @Date：2018/08/22
 */
@Data
public class DeleteCardDTO {
    private static final long serialVersionUID = 1L;

    // 证联支付分配给商户的机构代码 最大长度9位 不可为空
    private String instuId;

    // 商户的系统日期，YYYYMMDD 最大长度8位 不可为空
    private String fundDate;

    // 商户的时间戳，HHMMSS 最大长度6位 不可为空
    private String fundTime;

    // 商户系统流水号。需要保证一个交易日中流水号的唯一性 最大长度32位 不可为空
    private String fundSeqId;

    //用户在证联支付平台里的客户号 最大长度12位 不可为空
    private String userId;

    // 客户的名称 最大长度120位 不可为空
    private String userNameText;

    // 证件类型，参考附录 最大长度2位 不可为空
    private String certType;

    // 证件号码 最大长度32位 不可为空
    private String certId;

    // 银行代码 最大长度4位 不可为空
    private String bankCode;

    // 省份代码 最大长度4位 可为空
    private String bankProvinceCode;

    // 地区代码 最大长度4位 可为空
    private String bankRegionCode;

    // 银行卡号 最大长度32位 不可为空
    private String cardNo;

    // 保留域，接收方必须原样回送该字段数据 最大长度128位 可为空
    private String resv;

}
