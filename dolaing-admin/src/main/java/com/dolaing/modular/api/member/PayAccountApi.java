package com.dolaing.modular.api.member;

import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.api.base.Result;
import com.dolaing.modular.member.model.UserAccountRecord;
import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.modular.member.service.IAccountRecordService;
import com.dolaing.modular.member.service.IPayAccountService;
import com.dolaing.pay.client.entity.zlian.MarginRegisterDTO;
import com.dolaing.pay.client.entity.zlian.MarginSmsDTO;
import com.dolaing.pay.client.enums.PaymentEnum;
import com.dolaing.pay.client.utils.IdUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author:zhanglihua
 * @Description: 开户api
 * @Date：Created in 11:33 2018/7/29
 * @Modified By:
 */
@RestController
@RequestMapping("/dolaing/payAccount")
public class PayAccountApi extends BaseApi {
    @Autowired
    private IPayAccountService payAccountService;

    @ApiOperation(value = "注册开户")
    @RequestMapping("/marginRegister")
    public Result marginRegister(@RequestBody MarginRegisterDTO marginRegisterDTO) {
        //  2018-05-23  证联开户接口
        System.out.println("开户数据---->"+marginRegisterDTO.toDtoString());
        //演示数据
//        Map map = payAccountService.marginRegister(marginRegisterDTO);
        //return render(map);
        return render(null);
    }

    @ApiOperation(value = "查询开户信息")
    @RequestMapping("/queryUserPayAccount/{userId}")
    public Result getUserPayAccountByUserId(@PathVariable String userId) {
        UserPayAccount userPayAccount= new UserPayAccount();
        userPayAccount.setUserId(userId);
        userPayAccount.setPayment(PaymentEnum.PAY_ZLIAN.getCode());
        userPayAccount = payAccountService.getUserPayAccountByUserId(userPayAccount);
        return render(userPayAccount);
    }

    /**
     * @Author: 张立华
     * @Description: 转账业务
     * @params: *
     * @return: *
     * @Date: 12:28 2018/5/22
     */
    @ApiOperation(value = "开户短信")
    @RequestMapping(value="/marginRegisterSms",method = RequestMethod.POST)
    public Result marginRegisterSms(@RequestBody UserPayAccount userPayAccount) {
        MarginSmsDTO marginSmsDTO  = new MarginSmsDTO();
        marginSmsDTO.setUserNameText(userPayAccount.getUserNameText());
        marginSmsDTO.setMobile(userPayAccount.getMobile());
        marginSmsDTO.setBankCode(userPayAccount.getBankCode());
        marginSmsDTO.setCardNo(userPayAccount.getCardNo());
        marginSmsDTO.setCertType(userPayAccount.getCertType());
        marginSmsDTO.setCertId(userPayAccount.getCertId());
        marginSmsDTO.setMerchantSeqId(IdUtil.randomBase62(32));
        System.out.println("开户短信数据---->"+marginSmsDTO.toDtoString());
        //todo 演示模式
        Map map = payAccountService.marginRegisterSms(marginSmsDTO);
//        return render(map) ;
        return render(null);
    }
}
