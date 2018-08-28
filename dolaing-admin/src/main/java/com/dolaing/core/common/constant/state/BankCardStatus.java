package com.dolaing.core.common.constant.state;

/**
 * 银行卡的状态
 * <p>
 * Created by 王柳
 * Date 2018/8/27 15:03
 * version:1.0
 */
public enum BankCardStatus {

    OK("0", "启用"), DELETED("1", "删除");

    String code;
    String message;

    BankCardStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String labelOf(String value) {
        if (value == null) {
            return "";
        } else {
            for (BankCardStatus ms : BankCardStatus.values()) {
                if (ms.getCode().equals(value)) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
