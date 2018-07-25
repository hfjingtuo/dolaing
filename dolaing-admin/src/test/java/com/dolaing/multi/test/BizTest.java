package com.dolaing.multi.test;

import com.dolaing.base.BaseJunit;
import com.dolaing.multi.service.TestService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 业务测试
 *
 * @author zx
 * @date 2018-06-23 23:12
 */
public class BizTest extends BaseJunit {

    @Autowired
    TestService testService;

    @Test
    public void test() {
        testService.testDolaing();

        testService.testBiz();

        //testService.testAll();
    }
}
