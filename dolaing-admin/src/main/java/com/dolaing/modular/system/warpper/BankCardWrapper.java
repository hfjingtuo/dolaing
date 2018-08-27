package com.dolaing.modular.system.warpper;

import com.dolaing.core.base.warpper.BaseControllerWarpper;
import com.dolaing.core.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * 银行卡列表的包装
 *
 * @author zx
 * @date 2018年8月27日
 */
public class BankCardWrapper extends BaseControllerWarpper {

    public BankCardWrapper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        Integer creater = (Integer) map.get("creater");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }

}
