package com.dolaing.modular.member.service.impl;

import com.dolaing.modular.mall.vo.UserAccountRecordVo;
import com.dolaing.modular.member.dao.PayAccountMapper;
import com.dolaing.modular.member.model.UserAccountRecord;
import com.dolaing.modular.member.dao.AccountRecordMapper;
import com.dolaing.modular.member.service.IAccountRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhanglihua
 * @since 2018-08-01
 */
@Service
public class AccountRecordServiceImpl extends ServiceImpl<AccountRecordMapper, UserAccountRecord> implements IAccountRecordService {
    @Resource
    private AccountRecordMapper accountRecordMapper;

    @Override
    public List<UserAccountRecord> queryRecordsByUser(UserAccountRecord userAccountRecord) {
        return accountRecordMapper.queryRecordsByUser(userAccountRecord);
    }

    @Override
    public UserAccountRecordVo queryPayDetail(Integer orderId, String account, Integer processType) {
        return accountRecordMapper.queryPayDetail(orderId,account,processType);
    }

//    /**
//     * @Author: 张立华
//     * @Description: 根据交易流水号查询交易记录
//     * @params: *
//     * @return: *
//     * @Date: 21:18 2018/6/5
//     */
//    public UserTransactionRecord queryRecordBySeqId(UserTransactionRecord userTransactionRecord) {
//        return dao.queryRecordBySeqId(userTransactionRecord);
//    }
//
//    public List<UserTransactionRecord> queryRecordsByTypeAndStatus(UserTransactionRecord userTransactionRecord) {
//        return dao.queryRecordsByTypeAndStatus(userTransactionRecord);
//    }
//
//    public void batchUpdateStatus(List<UserTransactionRecord> userTransactionRecords){
//        dao.batchUpdateStatus(userTransactionRecords);
//    }
}
