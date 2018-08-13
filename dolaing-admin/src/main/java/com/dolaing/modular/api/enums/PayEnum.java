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

    NO_PAY_ACCOUNT("0", "您暂未开户，请开户后再支付。"),
    PAY_PASSWORD_ERR("1", "您的支付密码错误"),
    BALANCE_LOW ("2", "您的账户余额不足"),
    PAIED ("3", "该订单已支付,请勿重复支付。"),
    OTHER ("4", "其他错误"),
    NO_ORDER("5", "不存在该订单"),
    NOT_PAY_STATUS("6", "该订单不处于待付款状态。"),
    NO_RIGHT("7", "您无权限查看该订单"),
    SYS_ERR ("99", "系统异常"),
    SUCCESS("1000","支付成功"),
    ;

    private String code;

    private String message;
    PayEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
