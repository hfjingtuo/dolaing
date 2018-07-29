package com.dolaing.modular.api.enums;

import com.dolaing.modular.api.base.IResult;
import lombok.Getter;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 11:56 2018/7/29
 * @Modified By:
 */

@Getter
public enum ResultEnum implements IResult {

    SUCCESS("1000", "成功"),
    Fail("1001", "失败"),
    ;

    private String code;

    private String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
