package com.dolaing.modular.system.vo;

import com.dolaing.core.base.model.BaseModel;
import com.dolaing.modular.mall.model.MallShop;
import com.dolaing.modular.mall.vo.MallShopVo;
import com.dolaing.modular.system.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 10:07 2018/8/4
 * @Modified By:
 */
@Data
public class UserCacheVo extends BaseModel<UserCacheVo> {
    /**
     * 头像
     */
    private String avatar;

    /**
     * 账号
     */
    private String account;


    /**
     * 昵称
     */
    private String name;

    /**
     * 0 系统管理员  1 买家 2 卖家 3 农户 （暂定四种）
     */
    private String type;
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
     * 状态(1：启用  2：冻结  3：删除）
     */
    private int status;

    /**
     * 注册时间
     */
    private Date regTime;
    /**
     * 最后登录时间
     */
    private Date lastLogin;

    private MallShopVo mallShopVo ;

    public UserCacheVo(User user) {
        this.avatar = user.getAvatar();
        this.account = user.getAccount();
        this.birthday = user.getBirthday();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.name = user.getName();
        this.type = user.getType();
        this.status = user.getStatus();
        this.regTime = user.getRegTime();
        this.lastLogin = user.getLastLogin();
    }
}
