package com.dolaing.pay.client.constants;

import com.dolaing.pay.client.enums.PaymentEnum;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 21:37 2018/5/24
 * @Modified By:
 */
public class Global {

    public static final PaymentEnum DEFAULT_PAY_PLATFORM = PaymentEnum.PAY_ZLIAN;
    /**
     * 证联支付接口根地址
     */
    public static final String PAY_ZLIAN_BASE_URL = "http://localhost:8099/gpm-pay/zlpay/demo/";
    /**
     * 证联开户接口地址
     */
    public static final String PAY_ZLIAN_MARGIN_REGISTER_URL = PAY_ZLIAN_BASE_URL+"marginRegister";
    /**
     * 证联短信接口地址
     */
    public static final String PAY_ZLIAN_MARGIN_SMS_URL = PAY_ZLIAN_BASE_URL+"marginSms";

    /**
     * 证联线上入金接口地址
     */
    public static final String PAY_ZLIAN_ONLINE_DEPOSIT_SHORT_URL = PAY_ZLIAN_BASE_URL+"onlineDepositShort";
    /**
     * 证联资金转出接口地址
     */
    public static final String PAY_ZLIAN_WITH_DRAW_NOTICE_URL = PAY_ZLIAN_BASE_URL+"withdrawNotice";
    /**
     * 证联状态查询接口地址
     */
    public static final String PAY_ZLIAN_TRANS_STATUS_URL = PAY_ZLIAN_BASE_URL+"tranStatusQuery";


//    public static final Map PAY_ZLIAN_RESPCODE_MAP = new HashMap<String, String>() {
//        {
//            put("RC00", "交易处理成功");
//            put("集群对讲", new GroupTalkFragment());
//            put("视通", new VideoCallFragment());
//            put("位置", new PositionFragment());
//            put("浏览", new BrowseFragment());
//            put("消息", new MsgFragment());
//            put("群组", new GroupFragment());
//            put("设置", null);
//            put("退出", null);
//        }
//    };
}
