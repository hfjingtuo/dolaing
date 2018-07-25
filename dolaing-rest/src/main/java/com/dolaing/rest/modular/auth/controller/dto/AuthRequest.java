package com.dolaing.rest.modular.auth.controller.dto;

import com.dolaing.rest.modular.auth.validator.dto.Credence;

/**
 * 认证的请求dto
 *
 * @author zx
 * @Date 2018/8/24 14:00
 */
public class AuthRequest implements Credence {

    private String userName;
    private String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String getCredenceName() {
        return this.userName;
    }

    @Override
    public String getCredenceCode() {
        return this.password;
    }
}
