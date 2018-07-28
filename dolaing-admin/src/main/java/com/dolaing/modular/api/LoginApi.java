package com.dolaing.modular.api;

import com.dolaing.core.base.controller.BaseController;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.common.constant.Const;
import com.dolaing.core.common.constant.state.ManagerStatus;
import com.dolaing.core.shiro.ShiroKit;
import com.dolaing.core.shiro.ShiroUser;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.modular.system.model.User;
import com.dolaing.modular.system.service.IUserService;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 接口控制器提供
 *
 * @author zx
 * @Date 2018/7/20 23:39
 */
@RestController
@RequestMapping("/dolaing")
public class LoginApi extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * api登录接口
     * 买家、卖家、农户
     */
    @PostMapping("/login")
    public Object auth(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        //获取数据库中的账号密码比对
        User user = userService.getUserByUserName(userName);
        if (user != null && !Const.USERT_TYPE_ADMIN.equals(user.getType())) {
            if (ManagerStatus.FREEZED.getCode() == user.getStatus() || ManagerStatus.DELETED.getCode() == user.getStatus()) {
                return new ErrorTip(500, "该帐号已被冻结");
            }
            String credentials = user.getPassword();
            String salt = user.getSalt();
            ByteSource credentialsSalt = new Md5Hash(salt);
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(new ShiroUser(), credentials, credentialsSalt, "");

            //校验用户账号密码
            HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
            md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
            md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);

            //封装请求账号密码为shiro可验证的token
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password.toCharArray());
            boolean passwordTrueFlag = md5CredentialsMatcher.doCredentialsMatch(usernamePasswordToken, simpleAuthenticationInfo);
            if (passwordTrueFlag) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("token", JwtTokenUtil.generateToken(String.valueOf(user.getId())));
                System.out.println("登录成功>>>>>" + JwtTokenUtil.generateToken(String.valueOf(user.getId())));
                return result;
            }
        }
        return new ErrorTip(500, "账号或密码错误");
    }
}

