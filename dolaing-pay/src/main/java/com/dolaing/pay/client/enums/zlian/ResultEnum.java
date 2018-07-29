package com.dolaing.pay.client.enums.zlian;

import lombok.Getter;

/**
 * Created by 廖师兄
 * 2017-06-11 18:56
 */
@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),
    LOGIN_FAIL(1, "登录失败, 登录信息不正确"),
    LOGOUT_SUCCESS(2, "登出成功"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
