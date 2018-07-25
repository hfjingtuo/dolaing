package com.dolaing.fastjson;

import com.alibaba.fastjson.JSON;
import com.dolaing.core.util.MD5Util;
import com.dolaing.rest.common.SimpleObject;
import com.dolaing.rest.modular.auth.converter.BaseTransferEntity;

/**
 * json测试
 *
 * @author zx
 * @date 2018-08-25 16:11
 */


public class JsonTest {

    public static void main(String[] args) {
        String randomKey = "1xm7hw";

        BaseTransferEntity baseTransferEntity = new BaseTransferEntity();
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setUser("fsn");
        baseTransferEntity.setObject("123123");

        String json = JSON.toJSONString(simpleObject);

        //md5签名
        String encrypt = MD5Util.encrypt(json + randomKey);
        baseTransferEntity.setSign(encrypt);

        System.out.println(JSON.toJSONString(baseTransferEntity));
    }
}
