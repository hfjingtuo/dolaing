package com.dolaing.core.util;

import com.dolaing.core.common.constant.Const;

import javax.servlet.http.HttpServletRequest;

/**
 * token工具类
 *
 * @author zx
 * @Date 2018/8/12 10:59
 */
public class TokenUtil {

    /**
     * 根据Authorization获取token
     */
    public static String getToken(HttpServletRequest request) {
        String requestHeader = request.getHeader(Const.AUTH_HEADER);
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
        }
        return authToken;
    }
}