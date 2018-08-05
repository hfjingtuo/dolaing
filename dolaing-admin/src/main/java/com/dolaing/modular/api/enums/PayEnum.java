package com.dolaing.modular.api.enums;

import com.dolaing.modular.api.base.IResult;
import lombok.Getter;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 1:41 2018/8/6
 * @Modified By:
 */
@Getter
public enum PayEnum implements IResult {
    NO_PAY_ACCOUNT("0", "没有开户"),
    PAY_PASSWORD_ERR("1", "支付密码错误"),
    BALANCE_LOW ("2", "余额不足"),
    PAIED ("3", "订单已支付"),
    SYS_ERR ("99", "系统异常"),
    OTHER ("4", "其他错误"),
    ;

    private String code;

    private String message;
    PayEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
