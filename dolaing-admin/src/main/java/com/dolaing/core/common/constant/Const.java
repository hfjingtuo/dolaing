package com.dolaing.core.common.constant;

/**
 * 系统常量
 *
 * @author zx
 * @date 2018年2月12日 下午9:42:53
 */
public interface Const {

    /**
     * 系统默认的管理员密码
     */
    String DEFAULT_PWD = "111111";

    /**
     * 管理员角色的名字
     */
    String ADMIN_NAME = "administrator";

    /**
     * 管理员id
     */
    Integer ADMIN_ID = 1;

    /**
     * 超级管理员角色id
     */
    Integer ADMIN_ROLE_ID = 1;

    /**
     * 接口文档的菜单名
     */
    String API_MENU_NAME = "接口文档";

    /**********系统管理员*******/
    String USERT_TYPE_ADMIN = "0";

    /***********买家************/
    String USERT_TYPE_MEMBER = "1";

    /**********卖家**********/
    String USERT_TYPE_MERCHANT = "2";

    /**********农户**********/
    String USERT_TYPE_FARMER = "3";

    /**
     * 短信验证码KEY
     */
    String MSG_CODE_SESSION_KEY = "MSG_CODE_SESSION_KEY";

}
