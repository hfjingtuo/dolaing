package com.dolaing.modular.system.transfer;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户传输bean
 * 
 * @author zx
 * @Date 2018/5/5 22:40
 */
@Data
public class UserDto{

	private Integer id;
	private String account;
	private String password;
	private String salt;
	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	private Integer sex;
	private String email;
	private String phone;
	private String roleId;
	private Integer status;
	private Date createTime;
	private Integer version;
	private String avatar;


}
