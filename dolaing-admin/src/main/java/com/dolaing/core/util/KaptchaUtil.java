package com.dolaing.core.util;

import com.dolaing.config.properties.DolaingProperties;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(DolaingProperties.class).getKaptchaOpen();
    }
}