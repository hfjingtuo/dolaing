package com.dolaing.pay.client.utils;

import com.alibaba.fastjson.JSONObject;
import com.dolaing.pay.client.constants.Global;
import com.dolaing.pay.client.entity.zlian.OnlineDepositShortDTO;
import com.dolaing.pay.client.entity.zlian.TranStatusDTO;
import com.dolaing.pay.client.entity.zlian.WithdrawNoticeDTO;

import java.util.Map;

/**
 * @Author:张立华
 * @Description: 支付工具
 * @Date：Created in 13:16 2018/6/5
 * @Modified By:
 */
public class PayUtil {
   //证联:线上入金
   public static Map onlineDepositShort(OnlineDepositShortDTO onlineDepositShortDTO){
       String onlineDepositShortDTOStr = JSONObject.toJSON(onlineDepositShortDTO).toString();
       String url = Global.PAY_ZLIAN_ONLINE_DEPOSIT_SHORT_URL ;
       Map map = HttpUtil.sendMsg(url,onlineDepositShortDTOStr);
       return map;
   }
    //证联:资金转出
    public static Map withdrawNotice(WithdrawNoticeDTO withdrawNoticeDTO){
        String onlineDepositShortDTOStr = JSONObject.toJSON(withdrawNoticeDTO).toString();
        String url = Global.PAY_ZLIAN_WITH_DRAW_NOTICE_URL ;
        Map map = HttpUtil.sendMsg(url,onlineDepositShortDTOStr);
        return map;
    }

    //证联:状态查询
    public static Map tranStatusQuery(TranStatusDTO tranStatusDTO){
        String tranStatusDTOStr = JSONObject.toJSON(tranStatusDTO).toString();
        String url = Global.PAY_ZLIAN_TRANS_STATUS_URL ;
        Map map = HttpUtil.sendMsg(url,tranStatusDTOStr);
        return map;
    }
}
