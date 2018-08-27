package com.dolaing.modular.system.controller;

import com.dolaing.core.base.controller.BaseController;
import com.dolaing.core.common.annotion.BussinessLog;
import com.dolaing.core.common.constant.dictmap.BankCardMap;
import com.dolaing.modular.member.service.IPayAccountService;
import com.dolaing.modular.system.warpper.BankCardWrapper;
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
    public Object list(String condition) {
        List<Map<String, Object>>  payAccountList = payAccountService.payAccountList(condition);
        return super.warpObject(new BankCardWrapper(payAccountList));
    }

    /**
     * 解绑银行卡
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "解绑银行卡", key = "payAccountId", dict = BankCardMap.class)
    public Object delete(@RequestParam Integer payAccountId) {
        this.payAccountService.deleteById(payAccountId);
        return SUCCESS_TIP;
    }
}
