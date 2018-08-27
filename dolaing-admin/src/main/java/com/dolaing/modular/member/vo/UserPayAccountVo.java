package com.dolaing.modular.member.vo;

import com.dolaing.core.base.model.BaseModel;
import com.dolaing.modular.member.model.UserPayAccount;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhanglihua
 * @since 2018-07-29
 */
@Data
public class UserPayAccountVo extends BaseModel<UserPayAccountVo> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 用户account
     */
    private String userId;
    /**
     * 支付平台
     */
    private String payment;
    /**
     * 开户人真实姓名
     */
    private String userNameText;
    /**
     * 支付平台中的客户类型  0：个人 1：企业 最大长度1位 不可为空
     */
    private String custType;

    /**
     * 银行代码 最大长度4位
     */
    private String bankCode;

    /**
     * 银行卡号
     */
    private String cardNoLastFour;

    public UserPayAccountVo() {

    }

    public UserPayAccountVo(UserPayAccount userPayAccount) {
        this.bankCode = userPayAccount.getBankCode() ;
        this.cardNoLastFour = userPayAccount.getCardNo().substring(userPayAccount.getCardNo().length() -4 , userPayAccount.getCardNo().length());
        this.custType = userPayAccount.getCustType() ;
        this.createTime = userPayAccount.getCreateTime() ;
        this.userNameText = userPayAccount.getUserNameText() ;
        this.userId = userPayAccount.getUserId() ;
    }

}
