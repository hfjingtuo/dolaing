package com.dolaing.pay.client.enums.zlian;

import lombok.Getter;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 20:26 2018/5/11
 * @Modified By:
 */
@Getter
public enum StatusTradeTypeEnum {
    TRANS_IN("01","资金转入"),
    TRANS_OUT("02","资金转出");
    private String code ;
    private String msg ;

    StatusTradeTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
