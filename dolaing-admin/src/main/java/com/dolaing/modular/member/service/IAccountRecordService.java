package com.dolaing.modular.member.service;

import com.dolaing.modular.mall.vo.UserAccountRecordVo;
import com.dolaing.modular.member.model.UserAccountRecord;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 查询支付流水详情
     * @param orderId
     * @return
     */
    UserAccountRecordVo queryPayDetail(@Param("orderId") Integer orderId, @Param("account") String account, @Param("processType") Integer processType);

}
