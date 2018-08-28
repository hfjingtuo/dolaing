package com.dolaing.modular.member.service;

import com.dolaing.modular.member.model.UserPayAccount;
import com.baomidou.mybatisplus.service.IService;
import com.dolaing.pay.client.entity.zlian.MarginRegisterDTO;
import com.dolaing.pay.client.entity.zlian.MarginSmsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhanglihua
 * @since 2018-07-29
 */
public interface IPayAccountService extends IService<UserPayAccount> {
    //查询开户信息
    UserPayAccount getUserPayAccountByUserId(UserPayAccount userPayAccount);
    Map marginRegister(String account ,MarginRegisterDTO marginRegisterDTO);
    Map marginRegisterSms(MarginSmsDTO marginSmsDTO);

    List<Map<String, Object>> selectPayAccountList(String condition);

    /**
     * 修改银行卡状态
     */
    int setStatus(@Param("bankCardId") Integer bankCardId, @Param("delFlag") String status);
}
