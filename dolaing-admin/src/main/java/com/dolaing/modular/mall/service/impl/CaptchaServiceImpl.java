package com.dolaing.modular.mall.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dolaing.modular.mall.dao.CaptchaMapper;
import com.dolaing.modular.mall.model.Captcha;
import com.dolaing.modular.mall.service.ICaptchaService;
import org.springframework.stereotype.Service;

/**
 * Author: zx
 * Date: Created in 2018/07/23 15:05
 * Copyright: Copyright (c) 2018
 */
@Service
public class CaptchaServiceImpl extends ServiceImpl<CaptchaMapper, Captcha> implements ICaptchaService {
}
