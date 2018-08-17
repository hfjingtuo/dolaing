package com.dolaing.modular.redis.service;

import com.dolaing.modular.redis.model.TokenModel;
import com.dolaing.modular.system.model.User;

/**
 * Author: zhangxin
 * Date: Created in 2018/05/25 23:13
 * Copyright: Copyright (c) 2018
 * Description： 对 token 进行操作的接口
 */
public interface RedisTokenService {

    /**
     * 创建一个 token 关联上指定用户
     * @param account 指定用户的 id
     * @return 生成的 token
     */
    TokenModel createTokenByAccount(String account);

    /**
     * 检查 token 是否有效
     * @param token
     * @return 是否有效
     */
    boolean checkToken(String token);

    /**
     * 根据token获取User
     * @param token
     * @return
     */
    User getUserByToken(String token);

    /**
     * 从字符串中解析 token
     * @param authentication 加密后的字符串
     * @return
     */
    TokenModel getTokenModel(String authentication);

    /**
     * 清除 token
     * @param token
     */
    void deleteToken(String token);

}
