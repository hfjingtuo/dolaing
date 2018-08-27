package com.dolaing.modular.member.dao;

import com.dolaing.modular.member.model.UserPayAccount;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

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

    List<Map<String, Object>> payAccountList(String condition);
}
