package com.dolaing.modular.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dolaing.core.shiro.ShiroKit;
import com.dolaing.modular.api.base.IResult;
import com.dolaing.modular.api.enums.SmsEnum;
import com.dolaing.modular.member.dao.PayAccountMapper;
import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.modular.member.service.IPayAccountService;
import com.dolaing.modular.system.model.User;
import com.dolaing.pay.client.constants.Global;
import com.dolaing.pay.client.entity.zlian.Common208Result;
import com.dolaing.pay.client.entity.zlian.Common2901Result;
import com.dolaing.pay.client.entity.zlian.MarginRegisterDTO;
import com.dolaing.pay.client.entity.zlian.MarginSmsDTO;
import com.dolaing.pay.client.enums.PaymentEnum;
import com.dolaing.pay.client.enums.zlian.SmsTradeTypeEnum;
import com.dolaing.pay.client.utils.HttpUtil;
import com.dolaing.pay.client.utils.IdUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
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
    @Override
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
    @Override
    @Transactional
    public Map marginRegister(String account ,MarginRegisterDTO marginRegisterDTO) {
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
            userPayAccount.setUserId(account);
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
            //添加支付密码
            User user =  new User().selectOne("account = {0}" ,account) ;
            user.setPayPassword(ShiroKit.md5(marginRegisterDTO.getPayPassWord(), String.valueOf(user.getId())));
            user.updateById();
        }
        return map ;
    }



}
