package com.dolaing.pay.client.enums;

import lombok.Getter;

/**
 * @Author:张立华
 * @Description: 支付平台
 * @Date：Created in 18:57 2018/5/11
 * @Modified By:
 */
@Getter
public enum PaymentEnum {
    PAY_ZLIAN("0","证联支付");

    private String code ;
    private String name ;

    PaymentEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
