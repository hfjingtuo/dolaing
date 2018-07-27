package com.dolaing.modular.mall.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.dolaing.core.base.model.BaseModel;
import lombok.Data;

/**
 * Author: zx
 * Date: Created in 2018/07/13 14:53
 * Copyright: Copyright (c) 2018
 * Description： 验证码临时记录
 */
@TableName("captcha")
@Data
public class Captcha extends BaseModel<Captcha> {

    private static final long serialVersionUID = 1L;

    /**
     * 被验证用户id
     */
    private String account;

    /**
     * 验证号:手机号或邮箱号
     */
    private String captchaNum;

    /**
     * 验证码
     */
    private String code;

    /**
     * 1手机   2邮箱
     */
    private String type;

    /**
     * 1等待验证   2已验证
     */
    private String status;
}
