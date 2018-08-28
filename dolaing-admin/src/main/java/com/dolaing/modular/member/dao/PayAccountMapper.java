package com.dolaing.modular.member.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dolaing.modular.member.model.UserPayAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhanglihua
 * @since 2018-07-29
 */
public interface PayAccountMapper extends BaseMapper<UserPayAccount> {
    UserPayAccount getUserPayAccountByUserId(UserPayAccount userPayAccount);

    List<Map<String, Object>> selectPayAccountList(@Param("condition") String condition);

    /**
     * 修改银行卡状态
     */
    int setStatus(@Param("bankCardId") Integer bankCardId, @Param("delFlag") String status);
}
