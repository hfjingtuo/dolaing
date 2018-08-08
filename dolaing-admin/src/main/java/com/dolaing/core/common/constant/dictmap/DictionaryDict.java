package com.dolaing.core.common.constant.dictmap;

import com.dolaing.core.common.constant.dictmap.base.AbstractDictMap;

public class DictionaryDict extends AbstractDictMap {

    @Override
    public void init() {
        put("id","字典id");
        put("dictName","字典名称");
        put("dictLabel","字典中文标签");
        put("dictEnLabel","字典英文标签");
        put("dictValue","字典值");
        put("remarks","备注");

    }

    @Override
    protected void initBeWrapped() {

    }


}
