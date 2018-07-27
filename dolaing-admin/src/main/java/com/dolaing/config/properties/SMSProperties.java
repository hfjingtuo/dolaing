package com.dolaing.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: zx
 * Date: Created in 2018/07/23 14:53
 * Copyright: Copyright (c) 2018
 * Description： 短信发送配置
 */
@Data
@Component
@ConfigurationProperties(prefix = SMSProperties.PREFIX)
public class SMSProperties {

    public static final String PREFIX = "dolaing.sms";

    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String templateCode;
}