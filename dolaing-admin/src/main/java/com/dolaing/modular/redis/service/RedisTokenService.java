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
     * @param userName 指定用户的 id
     * @return 生成的 token
     */
    TokenModel createTokenByAccount(String userName);

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
     * 清除 token
     * @param userName
     */
    void deleteToken(String userName);

}
