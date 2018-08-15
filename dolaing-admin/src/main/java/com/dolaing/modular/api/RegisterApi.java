package com.dolaing.modular.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.common.constant.Const;
import com.dolaing.core.common.constant.state.ManagerStatus;
import com.dolaing.core.shiro.ShiroKit;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.mall.model.Captcha;
import com.dolaing.modular.mall.model.OrderInfo;
import com.dolaing.modular.system.model.User;
import com.dolaing.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Author: zx
 * Date: Created in 2018/07/25 9:20
 * Copyright: Copyright (c) 2018
 * Description： 买家注册
 */
@RestController
@RequestMapping("/dolaing")
public class RegisterApi extends BaseApi {

    @Autowired
    private IUserService userService;

    /**
     * 注册：买家
     */
    @PostMapping("/register")
    public Object register() {
        String userName = super.getPara("userName");
        String password = super.getPara("password");
        String msgCode = super.getPara("msgCode");

        if (ToolUtil.isOneEmpty(userName, password, msgCode)) {
            return new ErrorTip(500, "参数有空值");
        }

        User registerUser = userService.getUserByUserName(userName); //判断手机号是否已经注册
        if (registerUser != null) {
            return new ErrorTip(501, "该手机号已被注册");
        }
        if (ToolUtil.isEmpty(password) || password.length() < 6 || password.length() > 20) {
            return new ErrorTip(502, "密码长度为6-20位");
        }

        //手机与获取到的验证码 是否匹配且5分钟内 防止获取验证码后篡改用户名
        Wrapper<Captcha> wrapper = new EntityWrapper<>();
        Date now = new Date();
        wrapper.eq("captcha_num", userName);
        wrapper.eq("code", msgCode);
        wrapper.eq("status", "1");//等待验证的验证码
        wrapper.lt("create_time", now);
        wrapper.gt("create_time", new Date(now.getTime() - 5 * 60 * 1000));//5分钟内验证码有效
        List<Captcha> list = new Captcha().selectList(wrapper);
        if (list.isEmpty() || list.size() == 0) {
            return new ErrorTip(503, "短信验证码错误");
        } else {
            /**验证码验证过后使其失效 status置为2*/
            Captcha captcha = list.get(0);
            captcha.setStatus("2");
            captcha.updateById();
        }

        User user = new User();
        user.setAccount(getAccount());
        String salt = ShiroKit.getRandomSalt(5);
        user.setPassword(ShiroKit.md5(password, salt));
        user.setSalt(salt);
        user.setStatus(ManagerStatus.OK.getCode());
        user.setType(Const.USERT_TYPE_MEMBER);
        user.setPhone(userName);
        user.setCreateTime(new Date());
        user.setRegTime(new Date());
        user.setCreateBy(userName);
        this.userService.insert(user);
        return SUCCESS_TIP;
    }

    /**
     * 检验手机号是否已经被注册
     */
    @PostMapping("/validUserName")
    public Object validUserName() {
        String userName = super.getPara("userName");
        if (ToolUtil.isNotEmpty(userName)) {
            User user = userService.getUserByUserName(userName);//判断手机号是否已经注册
            if (user != null) {
                return new ErrorTip(501, "该手机号已被注册");
            }
        }
        return SUCCESS_TIP;
    }

    /**
     * 生成用户名
     *
     * @return account
     */
    public String getAccount() {
        String account;
        String maxAccount;
        User user;
        Wrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("type", Const.USERT_TYPE_MEMBER);
        wrapper.orderBy("account", false);
        Page<User> page = new Page<>(1, 1);
        Page<User> users = userService.selectPage(page, wrapper);
        if (users != null && users.getRecords() != null && users.getRecords().size() > 0) {
            user = users.getRecords().get(0);
            maxAccount = user.getAccount().substring(2, user.getAccount().length());
            Integer temp = Integer.valueOf(maxAccount);
            account = "DU" + String.format("%04d", temp + 1);
        } else {
            account = "DU0001";
        }
        System.out.println("account=" + account);
        return account;
    }
}
