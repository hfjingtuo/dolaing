package com.dolaing.modular.member.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.dolaing.core.base.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhanglihua
 * @since 2018-07-29
 */
@TableName("user_pay_account")
@Data
public class UserPayAccount extends BaseModel<UserPayAccount> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户account
     */
    @TableField("user_id")
    private String userId;
    /**
     * 支付平台  1 为证联 暂时默认为 1
     */
    private String payment;
    /**
     * 开户客户号
     */
    @TableField("pay_user_Id")
    private String payUserId;
    /**
     * 开户人真实姓名
     */
    @TableField("user_name_text")
    private String userNameText;
    /**
     * 支付平台中的客户类型  0：个人 1：企业 最大长度1位 不可为空
     */
    @TableField("cust_type")
    private String custType;
    /**
     * 证件类型，参考附录 最大长度2位 不可为空
     */
    @TableField("cert_type")
    private String certType;
    /**
     * 证件号码 最大长度32位 不可为空
     */
    @TableField("cert_id")
    private String certId;
    /**
     * 组织机构代码 注：（企业客户开户不允许为空，个人客户可空）  最大长度32位 可空
     */
    @TableField("org_id")
    private String orgId;
    /**
     * 银行代码 最大长度4位
     */
    @TableField("bank_code")
    private String bankCode;
    /**
     * 省份代码 最大长度4位
     */
    @TableField("bank_province_code")
    private String bankProvinceCode;
    /**
     * 地区代码
     */
    @TableField("bank_region_code")
    private String bankRegionCode;
    /**
     * 银行卡号
     */
    @TableField("card_No")
    private String cardNo;
    /**
     * 银行绑定手机号码
     */
    private String mobile;
    private String remarks;
    /**
     * 平台资金账户密码(保证金支付使用)
     */
    @TableField("account_psw")
    private String accountPsw;

    @TableField("del_flag")
    private String delFlag;
}
