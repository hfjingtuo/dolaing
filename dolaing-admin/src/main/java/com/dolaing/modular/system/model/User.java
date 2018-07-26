package com.dolaing.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.dolaing.core.base.model.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author zx
 * @since 2018-07-11
 */
@Data
@TableName("sys_user")
public class User extends BaseModel<User> {

	private static final long serialVersionUID = 1L;
	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 账号
	 */
	private String account;


	/**
	 * 密码
	 */
	private String password;

	/**
	 * md5密码盐
	 */
	private String salt;

	/**
	 * 昵称
	 */
	private String name;

	/**
	 * 0 系统管理员  1 买家 2 卖家 3 农户 （暂定四种）
	 */
	private String type ;
	/**
	 * 生日
	 */
	private Date birthday;

	/**
	 * 性别（1：男 2：女）
	 */
	private Integer sex;

	/**
	 * 电子邮件
	 */
	private String email;

	/**
	 * 手机
	 */
	private String phone;

	/**
	 * 角色id
	 */
	private String roleId;

	/**
	 * 状态(1：启用  2：冻结  0：删除）
	 */
	private int status;

	/**
	 * 注册时间
	 */
	private Date regTime;
	/**
	 * 最后登录时间
	 */
	private Date lastLogin ;
	/**
	 * 用户现有资金
	 */
    private BigDecimal userMoney ;
	/**
	 * 用户冻结资金
	 */
	private BigDecimal frozenMoney ;
	/**
	 * 支付密码
	 */
	private	String payPassword ;

}
