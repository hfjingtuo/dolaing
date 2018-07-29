package com.dolaing.modular.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.modular.system.dao.DictMapper;
import com.dolaing.modular.system.dao.PayAccountMapper;
import com.dolaing.modular.member.service.IPayAccountService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dolaing.pay.client.constants.Global;
import com.dolaing.pay.client.entity.zlian.Common208Result;
import com.dolaing.pay.client.entity.zlian.MarginRegisterDTO;
import com.dolaing.pay.client.entity.zlian.MarginSmsDTO;
import com.dolaing.pay.client.enums.PaymentEnum;
import com.dolaing.pay.client.enums.zlian.SmsTradeTypeEnum;
import com.dolaing.pay.client.utils.HttpUtil;
import com.dolaing.pay.client.utils.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhanglihua
 * @since 2018-07-29
 */
@Service
public class PayAccountServiceImpl extends ServiceImpl<PayAccountMapper, UserPayAccount> implements IPayAccountService {
    @Resource
    private PayAccountMapper payAccountMapper;


    /**
     * 查询开户信息
     * @param userPayAccount
     * @return
     */
    @Override
    public UserPayAccount getUserPayAccountByUserId(UserPayAccount userPayAccount) {
        return payAccountMapper.getUserPayAccountByUserId(userPayAccount);
    }

    /**
     * @Author: 张立华
     * @Description: 开户注册短信发送
     * @params: *
     * @return: *
     * @Date: 11:37 2018/5/24
     */
    public Map marginRegisterSms(MarginSmsDTO marginSmsDTO){
        marginSmsDTO.setTradeType(SmsTradeTypeEnum.PROTOCOL_REGISTRATION.getCode());
        String marginSmsDTOStr = JSONObject.toJSON(marginSmsDTO).toString();
        String url = Global.PAY_ZLIAN_MARGIN_SMS_URL ;
        return HttpUtil.sendMsg(url,marginSmsDTOStr);
    }

    /**
     * 开户（证联）
     * @param marginRegisterDTO
     * @return
     */
    @Transactional
    public Map marginRegister(MarginRegisterDTO marginRegisterDTO) {
        String userId = marginRegisterDTO.getResv();
        marginRegisterDTO.setResv("");
        marginRegisterDTO.setFundSeqId(IdUtil.randomBase62(32));
        String marginSmsDTOStr = JSONObject.toJSON(marginRegisterDTO).toString();
        String url = Global.PAY_ZLIAN_MARGIN_REGISTER_URL;
        Map map = HttpUtil.sendMsg(url,marginSmsDTOStr);
        Common208Result common208Result =
                JSONObject.toJavaObject((JSONObject)map.get("data"),Common208Result.class);
        if(common208Result !=null && common208Result.getRespCode().toString().equals("RC00")){
            System.out.println(common208Result.toString());
            //开户成功，将结果写入到数据库中
            UserPayAccount userPayAccount = new UserPayAccount();
            userPayAccount.setUserId(userId);
            userPayAccount.setUserNameText(marginRegisterDTO.getUserNameText());
            userPayAccount.setPayment(PaymentEnum.PAY_ZLIAN.getCode());
            userPayAccount.setBankCode(marginRegisterDTO.getBankCode());
            userPayAccount.setBankProvinceCode(marginRegisterDTO.getBankProvinceCode());
            userPayAccount.setBankRegionCode(marginRegisterDTO.getBankRegionCode());
            userPayAccount.setCardNo(marginRegisterDTO.getCardNo());
            userPayAccount.setCertId(marginRegisterDTO.getCertId());
            userPayAccount.setCertType(marginRegisterDTO.getCertType());
            userPayAccount.setCustType(marginRegisterDTO.getCustType());
            userPayAccount.setMobile(marginRegisterDTO.getMobile());
            userPayAccount.setOrgId(marginRegisterDTO.getOrgId());
            userPayAccount.setPayUserId(common208Result.getUserId());
            super.insert(userPayAccount);
//            //添加一个账号
//            UserAccount userAccount = new UserAccount();
//            userAccount.setUserId(userId);
//            userAccount.setAmount(new BigDecimal(0));
//            if(StringUtils.isNotBlank(marginRegisterDTO.getPayPassWord())){
//                userAccount.setPayPassword(SecretKeyUtil.payPassword(marginRegisterDTO.getPayPassWord()));
//            }
//            userAccount.setId(RandomUtil.randomUUID());
//            iUserAccountDao.insert(userAccount);
        }
        return map ;
    }
}
