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
import com.dolaing.modular.member.model.UserAccountRecord;
import com.dolaing.modular.member.service.IAccountRecordService;

import java.util.Date;

/**
 * 交易记录控制器
 *
 * @author zx
 * @Date 2018-08-01 16:11:30
 */
@Controller
@RequestMapping("/accountRecord")
public class AccountRecordController extends BaseController {

    private String PREFIX = "/member/accountRecord/";

    @Autowired
    private IAccountRecordService accountRecordService;

    /**
     * 跳转到交易记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "accountRecord.html";
    }

    /**
     * 跳转到添加交易记录
     */
    @RequestMapping("/accountRecord_add")
    public String accountRecordAdd() {
        return PREFIX + "accountRecord_add.html";
    }

    /**
     * 跳转到修改交易记录
     */
    @RequestMapping("/accountRecord_update/{accountRecordId}")
    public String accountRecordUpdate(@PathVariable Integer accountRecordId, Model model) {
        UserAccountRecord accountRecord = accountRecordService.selectById(accountRecordId);
        model.addAttribute("item",accountRecord);
        LogObjectHolder.me().set(accountRecord);
        return PREFIX + "accountRecord_edit.html";
    }

    /**
     * 获取交易记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return accountRecordService.selectList(null);
    }

    /**
     * 新增交易记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UserAccountRecord accountRecord) {
        accountRecordService.insert(accountRecord);
        return SUCCESS_TIP;
    }

    /**
     * 删除交易记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer accountRecordId) {
        accountRecordService.deleteById(accountRecordId);
        return SUCCESS_TIP;
    }

    /**
     * 修改交易记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UserAccountRecord accountRecord) {
        accountRecordService.updateById(accountRecord);
        return SUCCESS_TIP;
    }

    /**
     * 交易记录详情
     */
    @RequestMapping(value = "/detail/{accountRecordId}")
    @ResponseBody
    public Object detail(@PathVariable("accountRecordId") Integer accountRecordId) {
        return accountRecordService.selectById(accountRecordId);
    }

    public static void main(String[] args) {
        System.out.println(new Date());
    }
}
