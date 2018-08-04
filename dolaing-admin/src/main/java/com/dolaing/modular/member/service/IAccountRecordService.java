package com.dolaing.modular.member.service;

import com.dolaing.modular.member.model.UserAccountRecord;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhanglihua
 * @since 2018-08-01
 */
public interface IAccountRecordService extends IService<UserAccountRecord> {
    List<UserAccountRecord> queryRecordsByUser(UserAccountRecord userAccountRecord);
}
