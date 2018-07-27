package com.dolaing.modular.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.dolaing.core.base.controller.BaseController;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.base.tips.SuccessTip;
import com.dolaing.core.common.constant.Const;
import com.dolaing.core.util.RegisterCodeUtil;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.mall.model.Captcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Author: zx
 * Date: Created in 2018/07/05 9:20
 * Copyright: Copyright (c) 2018
 * Description： 短信验证码和邮件验证码获取
 */
@Controller
@RequestMapping(value = "/dolaing/code")
public class RegisterCodeApi extends BaseController {

    /**
     * 发送手机验证码
     */
    @ResponseBody
    @PostMapping("/msgCode")
    public Object sendMsgCode(HttpServletRequest request) {
        String phone = super.getPara("phone");
        if (ToolUtil.isNotEmpty(phone)) {
            String code = RegisterCodeUtil.randomCode();
            Wrapper<Captcha> wrapper = new EntityWrapper<>();
            wrapper.eq("captcha_num", phone);
            wrapper.orderDesc(Collections.singleton("create_time"));
            List<Captcha> list = new Captcha().selectList(wrapper);
            if (list != null && list.size() > 0) {
                Date createTime = list.get(0).getCreateTime();
                Long difftime = (new Date().getTime() - createTime.getTime()) / 1000;
                if (difftime < 60) {
                    return new ErrorTip(60 - difftime.intValue(), "60s内不允许重复发送验证码");
                }
            }
            //Boolean isSuccess = RegisterCodeUtil.sendMsg(phone);
            Boolean isSuccess = true;
            if (isSuccess) {
                /**验证码记录入库*/
                Captcha captcha = new Captcha();
                captcha.setCaptchaNum(phone);
                captcha.setCode(code);
                captcha.setStatus("1");
                captcha.setType("2");
                captcha.setCreateTime(new Date());
                captcha.setCreateBy(phone);
                captcha.insert();
                setAttr(Const.MSG_CODE_SESSION_KEY, code);
                System.out.println("短信验证码：" + code);
                return new SuccessTip();
            }
        }
        return new ErrorTip(502, "短信验证码发送异常,请稍候重试");
    }

}
