package com.dolaing.modular.member.dao;

import com.dolaing.modular.member.model.UserAccountRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhanglihua
 * @since 2018-08-01
 */
public interface AccountRecordMapper extends BaseMapper<UserAccountRecord> {
    List<UserAccountRecord> queryRecordsByUser(UserAccountRecord userAccountRecord);
}
