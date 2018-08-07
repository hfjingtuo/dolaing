package com.dolaing.modular.redis.model;

import lombok.Data;

/**
 * Author: zhangxin
 * Date: Created in 2018/05/25 23:12
 * Copyright: Copyright (c) 2018
 * Description： Token 的 Model 类，可以增加字段提高安全性，例如时间戳、url 签名
 */
@Data
public class TokenModel {

    // 用户帐号
    private String account;

    // 随机生成的 uuid
    private String token;

    public TokenModel(String account, String token) {
        this.account = account;
        this.token = token;
    }
}