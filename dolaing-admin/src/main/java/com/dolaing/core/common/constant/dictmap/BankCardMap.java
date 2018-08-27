package com.dolaing.core.common.constant.dictmap;

import com.dolaing.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * 银行卡的映射
 *
 * @author zx
 * @date 2018-05-06 15:01
 */
public class BankCardMap extends AbstractDictMap {

    @Override
    public void init() {
        put("title", "标题");
        put("content", "内容");
    }

    @Override
    protected void initBeWrapped() {
    }
}
