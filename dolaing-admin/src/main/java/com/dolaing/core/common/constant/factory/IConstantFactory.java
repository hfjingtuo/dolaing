package com.dolaing.core.common.constant.factory;

/**
 * 常量生产工厂的接口
 *
 * @author zx
 * @date 2018-06-14 21:12
 */
public interface IConstantFactory {

    /**
     * 根据用户id获取用户名称
     *
     * @author zx
     * @Date 2018/5/9 23:41
     */
    String getUserNameById(Integer userId);

    /**
     * 根据用户id获取用户账号
     *
     * @author zx
     * @date 2018年5月16日21:55:371
     */
    String getUserAccountById(Integer userId);

    /**
     * 通过角色ids获取角色名称
     */
    String getRoleName(String roleIds);
    /**
     * 通过用户类型名称
     */
    String getUserTypeName(String type);

    /**
     * 通过角色id获取角色名称
     */
    String getSingleRoleName(Integer roleId);

    /**
     * 通过角色id获取角色英文名称
     */
    String getSingleRoleTip(Integer roleId);

    /**
     * 获取菜单名称
     */
    String getMenuName(Long menuId);

    /**
     * 获取菜单名称通过编号
     */
    String getMenuNameByCode(String code);

    /**
     * 获取用户登录状态
     */
    String getStatusName(Integer status);

    /**
     * 获取菜单状态
     */
    String getMenuStatusName(Integer status);

    /**
     * 根据id获取银行卡号（去除后四位）
     */
    String getBankCardNoById(Integer bankCardId);

    /**
     * 获取客户类型名称
     */
    String getCustTypeName(String custType);

    /**
     * 获取支付平台名称
     */
    String getPaymentName(String payment);

}
