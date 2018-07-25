package com.dolaing.rest;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 2018 REST Web程序启动类
 *
 * @author zx
 * @date 2018年9月29日09:00:42
 */
public class DolaingRestServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DolaingRestApplication.class);
    }

}
