package com.dolaing.generator.action;


import com.dolaing.generator.action.config.DolaingGeneratorConfig;

/**
 * 代码生成器,可以生成实体,dao,service,controller,html,js
 *
 * @author zx
 * @Date 2018/5/21 12:38
 */
public class DolaingCodeGenerator {

    public static void main(String[] args) {

        /**
         * Mybatis-Plus的代码生成器:
         *      mp的代码生成器可以生成实体,mapper,mapper对应的xml,service
         */
        DolaingGeneratorConfig dolaingGeneratorConfig = new DolaingGeneratorConfig();
        dolaingGeneratorConfig.doMpGeneration();

        /**
         * dolaing的生成器:
         *      dolaing的代码生成器可以生成controller,html页面,页面对应的js
         */
        dolaingGeneratorConfig.doDolaingGeneration();
    }

}