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
        String cardNo = (String) map.get("cardNo");
        map.put("cardNoLastFour", cardNo.substring(0, cardNo.length() - 4) + "****");
        map.put("custTypeName", ConstantFactory.me().getCustTypeName((String) map.get("custType")));
        map.put("paymentName", ConstantFactory.me().getPaymentName((String) map.get("payment")));
    }

}
