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
public enum SmsEnum implements IResult {
    NETWORK_CONNECTION_TIMEOUT("0", "网络连接超时"),
    SEND_ERROR("1","发送失败，请稍后再试"),
    SYS_ERR ("99", "系统异常"),
    SUCCESS("1000","处理成功"),
    RC29("RC29","交易失败"),
    RC33("RC33","业务处理失败"),
    ;

    private String code;

    private String message;
    SmsEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
