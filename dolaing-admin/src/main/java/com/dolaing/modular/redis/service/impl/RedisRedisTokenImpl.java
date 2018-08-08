package com.dolaing.modular.redis.service.impl;

import com.alibaba.fastjson.JSON;
import com.dolaing.core.common.constant.Const;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.core.util.MD5Util;
import com.dolaing.modular.redis.model.TokenModel;
import com.dolaing.modular.redis.service.RedisTokenService;
import com.dolaing.modular.system.model.User;
import com.dolaing.modular.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Author: zhangxin
 * Date: Created in 2018/05/25 23:14
 * Copyright: Copyright (c) 2018
 * Description： 通过 Redis 存储和验证 token 的实现类
 */
@Component
public class RedisRedisTokenImpl implements RedisTokenService {

    @Autowired
    private IUserService userService;
    private RedisTemplate<String, String> redis;

    @Autowired
    public void setRedis(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redis = redisTemplate;
    }

    public TokenModel createTokenByAccount(String account) {
        String token = JwtTokenUtil.generateToken(String.valueOf(account));
        TokenModel model = new TokenModel(account, token);
        User user = userService.getByAccount(account);
        User newUser = new User();
        newUser.setAccount(account);
        newUser.setPhone(user.getPhone());
        newUser.setEmail(user.getEmail());
        String userJson = JSON.toJSONString(newUser);
        // 存储到 redis 并设置过期时间
        redis.boundValueOps(token).set(userJson, Const.TOKEN_EXPIRED, TimeUnit.MINUTES);
        return model;
    }

    public User getUserByToken(String token) {
        User user = null;
        String values = redis.boundValueOps(token).get();
        if (StringUtils.isNotBlank(values)) {
            user = JSON.parseObject(values, User.class);
        }
        return user;
    }

    public boolean checkToken(String token) {
        if (token == null) {
            return false;
        }
        String values = redis.boundValueOps(token).get();
        if (values == null) {
            return false;
        }
        // 如果验证成功，说明此用户进行了一次有效操作，延长 token 的过期时间
        redis.boundValueOps(token).expire(Const.TOKEN_EXPIRED, TimeUnit.MINUTES);
        return true;
    }

    public void deleteToken(String userName) {
        redis.delete(userName);
    }
}