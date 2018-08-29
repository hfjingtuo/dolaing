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
        put("account", "账号");
        put("cardNoLastFour", "银行卡尾号");
        put("bankCardId", "银行卡尾号");
        put("title", "标题");
        put("content", "内容");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("bankCardId","getBankCardNoById");
    }
}
