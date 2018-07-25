package com.dolaing.core.common.constant;

/**
 * jwt相关配置
 *
 * @author zx
 * @date 2018-08-23 9:23
 */
public interface JwtConstants {

    String AUTH_HEADER = "Authorization";

    String SECRET = "defaultSecret";

    Long EXPIRATION = 604800L;

    String AUTH_PATH = "/dolaingApi/auth";

}
