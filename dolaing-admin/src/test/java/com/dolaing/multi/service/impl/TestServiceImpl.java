package com.dolaing.multi.service.impl;

import com.dolaing.core.common.constant.DatasourceEnum;
import com.dolaing.core.mutidatasource.annotion.DataSource;
import com.dolaing.multi.entity.Test;
import com.dolaing.multi.mapper.TestMapper;
import com.dolaing.multi.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zx
 * @since 2018-07-10
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_BIZ)
    @Transactional
    public void testBiz() {
        Test test = new Test();
        test.setBbb("bizTest");
        testMapper.insert(test);
    }

    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_DOLAING)
    @Transactional
    public void testDolaing() {
        Test test = new Test();
        test.setBbb("dolaingTest");
        testMapper.insert(test);
    }

    @Override
    @Transactional
    public void testAll() {
        testBiz();
        testDolaing();
        //int i = 1 / 0;
    }

}
