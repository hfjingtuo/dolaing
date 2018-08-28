package com.dolaing.modular.system.controller;

import com.dolaing.core.base.controller.BaseController;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.common.annotion.BussinessLog;
import com.dolaing.core.common.annotion.Permission;
import com.dolaing.core.common.constant.dictmap.BankCardMap;
import com.dolaing.core.common.constant.state.BankCardStatus;
import com.dolaing.core.common.exception.BizExceptionEnum;
import com.dolaing.core.exception.DolaingException;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.modular.member.service.IPayAccountService;
import com.dolaing.modular.system.warpper.BankCardWrapper;
import com.dolaing.pay.client.entity.zlian.DeleteCardDTO;
import com.dolaing.pay.client.enums.zlian.BankCodeEnum;
import com.dolaing.pay.client.enums.zlian.CertTypeEnum;
import com.dolaing.pay.client.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 解绑银行卡
 *
 * @author zx
 * @Date 2018-08-27
 */
@Controller
@RequestMapping("/bankCard")
public class BankCardController extends BaseController {

    private String PREFIX = "/system/bankCard/";

    @Autowired
    private IPayAccountService payAccountService;

    /**
     * 跳转到通知列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "bankCard.html";
    }

    /**
     * 获取银行卡
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @Permission
    public Object list(String condition) {
        List<Map<String, Object>> payAccountList = payAccountService.selectPayAccountList(condition);
        return super.warpObject(new BankCardWrapper(payAccountList));
    }

    /**
     * 解绑银行卡
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Permission
    @BussinessLog(value = "解绑银行卡", key = "bankCardId", dict = BankCardMap.class)
    public Object delete(@RequestParam Integer bankCardId) {
        if (ToolUtil.isEmpty(bankCardId)) {
            throw new DolaingException(BizExceptionEnum.REQUEST_NULL);
        }
        UserPayAccount userPayAccount = payAccountService.selectById(bankCardId);
        if (userPayAccount != null) {
            DeleteCardDTO deleteCardDTO = new DeleteCardDTO();
            deleteCardDTO.setFundSeqId(IdUtil.randomBase62(32));
            deleteCardDTO.setUserId(userPayAccount.getUserId());
            deleteCardDTO.setUserNameText(userPayAccount.getUserNameText());
            deleteCardDTO.setCertType(CertTypeEnum.NATIONAL_IDENTITY_CARD.getCode());
            deleteCardDTO.setCertId(userPayAccount.getCertId());
            deleteCardDTO.setBankCode(BankCodeEnum.CHINA_EVERBRIGHT_BANK.getCode());
            deleteCardDTO.setBankProvinceCode("");
            deleteCardDTO.setBankRegionCode("");
            deleteCardDTO.setCardNo(userPayAccount.getCardNo());
            deleteCardDTO.setResv("");
            Map map = payAccountService.deleteCard(bankCardId, BankCardStatus.DELETED.getCode(), deleteCardDTO);
            if (map.get("code").toString().equals("1000")) {
                return SUCCESS_TIP;
            }else {
                return new ErrorTip(500, map.get("msg").toString());
            }
        }
        return new ErrorTip(500, "解绑银行卡失败");
    }
}
