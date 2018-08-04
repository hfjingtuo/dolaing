package com.dolaing.modular.member.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
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
 * @since 2018-08-01
 */
@TableName("user_account_record")
@Data
public class UserAccountRecord  extends BaseModel<UserAccountRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户账户流水
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 数据来源id
     */
    @TableField("source_id")
    private String sourceId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;
    /**
     * 操作该笔交易的管理员的用户名
     */
    @TableField("admin_user")
    private String adminUser;
    /**
     * 资金的数目，正数为增加，负数为减少
     */
    private String amount;
    /**
     * 操作类型，1，转入；2，预付费
     */
    @TableField("process_type")
    private String processType;
    /**
     * 支付渠道1 证联支付
     */
    private String payment;

    @TableField("remarks")
    private Date remarks;


}
