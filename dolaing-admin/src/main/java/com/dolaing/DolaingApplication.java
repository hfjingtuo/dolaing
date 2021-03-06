package com.dolaing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SpringBoot方式启动类
 *
 * @author zx
 * @Date 2018/5/21 12:06
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class DolaingApplication {

    private final static Logger logger = LoggerFactory.getLogger(DolaingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DolaingApplication.class, args);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<DolaingApplication is success!>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
