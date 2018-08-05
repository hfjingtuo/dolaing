package com.dolaing.core.util;


import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.pay.client.entity.zlian.OnlineDepositShortDTO;
import com.dolaing.pay.client.entity.zlian.WithdrawNoticeDTO;

/**
 * @Author:张立华
 * @Description: 实体转换Dto接口对象工具类
 * @Date：Created in 14:45 2018/6/5
 * @Modified By:
 */
public class DtoTransUtil {

    //用户支付平台账户转证联线上入金接口对象
    public static void userPayAccountToOnlineDepositShortDto(OnlineDepositShortDTO onlineDepositShortDTO , UserPayAccount userPayAccount){
        onlineDepositShortDTO.setUserId(userPayAccount.getPayUserId());
        onlineDepositShortDTO.setUserNameText(userPayAccount.getUserNameText());
        onlineDepositShortDTO.setCertType(userPayAccount.getCertType());
        onlineDepositShortDTO.setCertId(userPayAccount.getCertId());
        onlineDepositShortDTO.setCardNo(userPayAccount.getCardNo());
        onlineDepositShortDTO.setBankCode(userPayAccount.getBankCode());
        onlineDepositShortDTO.setAccountPsw(userPayAccount.getAccountPsw());
    }

    //用户支付平台账户转证联线上入金接口对象
    public static void userPayAccountToWithdrawNoticeDTO(WithdrawNoticeDTO withdrawNoticeDTO , UserPayAccount userPayAccount){
        withdrawNoticeDTO.setUserId(userPayAccount.getPayUserId());
        withdrawNoticeDTO.setUserNameText(userPayAccount.getUserNameText());
        withdrawNoticeDTO.setBankCode(userPayAccount.getBankCode());
        withdrawNoticeDTO.setCardName(userPayAccount.getUserNameText());
        withdrawNoticeDTO.setCardNo(userPayAccount.getCardNo());
    }



}
