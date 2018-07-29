package com.dolaing.modular.system.dao;

import com.dolaing.modular.member.model.UserPayAccount;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhanglihua
 * @since 2018-07-29
 */
public interface PayAccountMapper extends BaseMapper<UserPayAccount> {
    UserPayAccount getUserPayAccountByUserId(UserPayAccount userPayAccount);
}
