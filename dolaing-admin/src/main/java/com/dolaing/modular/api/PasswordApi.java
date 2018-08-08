package com.dolaing.modular.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.common.constant.JwtConstants;
import com.dolaing.core.shiro.ShiroKit;
import com.dolaing.core.support.HttpKit;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.mall.model.Captcha;
import com.dolaing.modular.mall.vo.EditPasswordVo;
import com.dolaing.modular.redis.service.RedisTokenService;
import com.dolaing.modular.system.model.User;
import com.dolaing.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 修改密码、重置密码
 *
 * @Author 王柳
 * @Date 2018/8/2 16:17
 */
@RestController
@RequestMapping("/dolaing")
public class PasswordApi extends BaseApi {

    @Autowired
    private IUserService userService;

    /**
     * 修改密码
     */
    @PostMapping("/confirmSubmitEditPwd")
    public Object confirmSubmitEditPwd(@RequestBody EditPasswordVo password) {
        String oldPwd = password.getOldPwd();
        String newPwd = password.getNewPwd();
        String rePwd = password.getRePwd();
        if (ToolUtil.isOneEmpty(oldPwd, newPwd, rePwd)) {
            return new ErrorTip(500, "参数有空值");
        }
        String token = JwtTokenUtil.getToken(HttpKit.getRequest());
        String account = JwtTokenUtil.getAccountFromToken(token);
        User user = userService.getByAccount(account);
        String oldMd5 = ShiroKit.md5(oldPwd, user.getSalt());
        if (!user.getPassword().equals(oldMd5)) {
            return new ErrorTip(501, "原密码不正确");
        }
        if (ToolUtil.isEmpty(newPwd) || newPwd.length() < 6 || newPwd.length() > 20) {
            return new ErrorTip(502, "密码长度为6-20位");
        }
        if (!newPwd.equals(rePwd)) {
            return new ErrorTip(503, "两次密码输入不一致");
        }
        String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
        user.setPassword(newMd5);
        user.updateById();
        return SUCCESS_TIP;
    }

    /**
     * 忘记密码：重置密码
     */
    @PostMapping("/confirmSubmit")
    public Object confirmSubmit() {
        String userName = super.getPara("userName");
        String password = super.getPara("password");
        String msgCode = super.getPara("msgCode");

        if (ToolUtil.isOneEmpty(userName, password, msgCode)) {
            return new ErrorTip(500, "参数有空值");
        }

        User registerUser = userService.getUserByUserName(userName); //判断手机号是否已经注册
        if (registerUser == null) {
            return new ErrorTip(501, "该手机号未注册");
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
        user = user.selectOne("account = {0}", userName);
        String salt = ShiroKit.getRandomSalt(5);
        user.setPassword(ShiroKit.md5(password, salt));
        user.setSalt(salt);
        user.setUpdateBy(userName);
        user.setUpdateTime(new Date());
        this.userService.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 检验手机号是否已经被注册
     */
    @PostMapping("/validUserNameExist")
    public Object validUserNameExist() {
        String userName = super.getPara("userName");
        if (ToolUtil.isNotEmpty(userName)) {
            User user = userService.getUserByUserName(userName);//判断手机号是否已经注册
            if (user == null) {
                return new ErrorTip(555, "该手机号未注册");
            }
        }
        return SUCCESS_TIP;
    }
}
