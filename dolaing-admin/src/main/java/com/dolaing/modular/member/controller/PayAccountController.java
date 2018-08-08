package com.dolaing.modular.member.controller;

import com.dolaing.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.dolaing.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.modular.member.service.IPayAccountService;

/**
 * 开户信息控制器
 *
 * @author zhanglihua
 * @Date 2018-07-29 11:30:49
 */
@Controller
@RequestMapping("/payAccount")
public class PayAccountController extends BaseController {

    private String PREFIX = "/member/payAccount/";

    @Autowired
    private IPayAccountService payAccountService;


    /**
     * 跳转到修改开户信息
     */
    @RequestMapping("/payAccount_update/{payAccountId}")
    public String payAccountUpdate(@PathVariable Integer payAccountId, Model model) {
        UserPayAccount payAccount = payAccountService.selectById(payAccountId);
        model.addAttribute("item",payAccount);
        LogObjectHolder.me().set(payAccount);
        return PREFIX + "payAccount_edit.html";
    }

    /**
     * 获取开户信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return payAccountService.selectList(null);
    }

    /**
     * 新增开户信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserPayAccount payAccount) {
        payAccountService.insert(payAccount);
        return SUCCESS_TIP;
    }

    /**
     * 删除开户信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer payAccountId) {
        payAccountService.deleteById(payAccountId);
        return SUCCESS_TIP;
    }

    /**
     * 修改开户信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserPayAccount payAccount) {
        payAccountService.updateById(payAccount);
        return SUCCESS_TIP;
    }

    /**
     * 开户信息详情
     */
    @RequestMapping(value = "/detail/{payAccountId}")
    @ResponseBody
    public Object detail(@PathVariable("payAccountId") Integer payAccountId) {
        return payAccountService.selectById(payAccountId);
    }
}
