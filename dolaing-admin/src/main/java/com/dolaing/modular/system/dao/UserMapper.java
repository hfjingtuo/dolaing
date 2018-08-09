package com.dolaing.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dolaing.core.datascope.DataScope;
import com.dolaing.modular.system.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author zx
 * @since 2018-07-11
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 修改用户状态
     */
    int setStatus(@Param("userId") Integer userId, @Param("status") int status);

    /**
     * 修改密码
     */
    int changePwd(@Param("userId") Integer userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     */
    List<Map<String, Object>> selectUsers(@Param("dataScope") DataScope dataScope, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("type") String type);

    /**
     * 设置用户的角色
     */
    int setRoles(@Param("userId") Integer userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     */
    User getByAccount(@Param("account") String account);

    /**
     * 通过卖家账号获取农户
     */
    List<User> getFarmerByParentAccount(@Param("account") String account);

    /**
     * 通过帐号、邮箱、手机号获取为启用的用户
     * 【用于登录、找回密码、校验手机号或邮箱是否已被使用】
     */
    User getUserByUserName(@Param("condition") String condition);
}