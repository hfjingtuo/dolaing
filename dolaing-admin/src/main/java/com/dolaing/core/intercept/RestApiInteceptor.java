package com.dolaing.core.intercept;

import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.common.annotion.AuthAccess;
import com.dolaing.core.common.exception.BizExceptionEnum;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.core.util.RenderUtil;
import com.dolaing.modular.redis.service.RedisTokenService;
import com.dolaing.modular.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Rest Api接口鉴权
 *
 * @author zx
 * @Date 2018/7/20 23:11
 */
public class RestApiInteceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTokenService redisTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.addHeader("Access-Control-Max-Age", "100");//缓存的最长时间，单位是秒
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept");
        if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) {
            return true;
        }
        return check(request, response, handler);
    }

    private boolean check(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录认证
        AuthAccess methodAnnotation = method.getAnnotation(AuthAccess.class);
        // 有 @AuthAccess 注解，需要认证
        if (methodAnnotation != null) {
            //从http 请求头header中得到token
            String authToken = JwtTokenUtil.getToken(request);
            if (authToken == null) {
                RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
                return false;
            }
            //验证token
            if (redisTokenService.checkToken(authToken)) {
                User user = redisTokenService.getUserByToken(authToken);
                if (user == null) {
                    RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_EXPIRED.getCode(), BizExceptionEnum.TOKEN_EXPIRED.getMessage()));
                    return false;
                }
                return true;
            } else {
                RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
                return false;
            }
        }
        return true;
    }

}
