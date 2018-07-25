package com.dolaing.system;

import com.dolaing.base.BaseJunit;
import com.dolaing.modular.system.dao.NoticeMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * 首页通知展示测试
 *
 * @author zx
 * @date 2018-05-21 15:02
 */
public class BlackBoardTest extends BaseJunit {

    @Autowired
    NoticeMapper noticeMapper;

    @Test
    public void blackBoardTest() {
        List<Map<String, Object>> notices = noticeMapper.list(null);
        assertTrue(notices.size() > 0);
    }
}
