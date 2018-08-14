package com.dolaing.modular.api.enums;

import lombok.Getter;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 15:50 2018/8/5
 * @Modified By:
 */
@Getter
public enum OrderStatusEnum {
    UN_CONFIRMED("0", "未确认"),
    PENDING_PAYMENT("1", "待付款"),
    PRODUCTION("2", "生产中"),
    WAIT_FOR_RECEPTION ("3", "待收货"),
    COMPLETED("100", "已完成"),
    TIMEOUT_PAY("4", "已取消"),
    CANCELL("5", "已取消"),;
    private String code;
    private String message;
    OrderStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
