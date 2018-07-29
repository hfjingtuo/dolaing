package com.dolaing.pay.client.enums.zlian;

import lombok.Getter;

/**
 * @Author:张立华
 * @Description: 交易方式 [交易状态查询业务用到]
 * @Date：Created in 19:13 2018/5/11
 * @Modified By:
 */
@Getter
public enum TiedCardTypeEnum {
    QUICK("0","快捷"),
    GATEWAY("1","网关"),
    OFFLINE("2","线下");
    private String code ;
    private String name ;
    TiedCardTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
