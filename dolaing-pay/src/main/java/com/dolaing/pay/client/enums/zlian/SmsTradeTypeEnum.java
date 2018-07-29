package com.dolaing.pay.client.enums.zlian;

import lombok.Getter;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 20:26 2018/5/11
 * @Modified By:
 */
@Getter
public enum SmsTradeTypeEnum {
    PROTOCOL_REGISTRATION("01","协议注册类"),
    PROTOCOL_NEW_BANK_CARD("02","协议新增银行卡类"),
    PROTOCOL_TRADING ("03","协议交易类"),
    QUICK_RECHARGE_OPEN_ACCOUNT("04","快捷充值开户"),
    QUICK_RECHARGE_NEW_BANK_CARD("05","快捷充值新增银行卡"),
    FAST_TRADING("06","快捷交易(充值、认申购等)");

    private String code ;
    private String msg ;

    SmsTradeTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
