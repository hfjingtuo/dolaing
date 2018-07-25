package com.dolaing;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 2018 Web程序启动类
 *
 * @author zx
 * @date 2018-05-21 9:43
 */
public class DolaingServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DolaingApplication.class);
    }
}
