package com.dolaing.core.common.constant;

import java.math.BigDecimal;

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

    /**
     * 广告图片存放地址
     */
    String GOODS_IMG = "images/goods/";

    /**********系统管理员*******/
    String USERT_TYPE_ADMIN = "0";

    /***********买家************/
    String USERT_TYPE_MEMBER = "1";

    /**********卖家**********/
    String USERT_TYPE_MERCHANT = "2";

    /**********农户**********/
    String USERT_TYPE_FARMER = "3";

    /**********订单的状态:1 确认状态*************/
    Integer ORDER_STATUS_UNCONFIRMED = 1;
    /********订单的状态:3 无效****************/
    Integer ORDER_STATUS_EXPIRE = 3;
    /********付款状态;0未付款****************/
    Integer ORDER_PAY_STATUS = 0;


    //卖家应收金额比例：10%
    BigDecimal SELLERRECEIVABLEAMOUNT_RATE = BigDecimal.valueOf(0.1);

    //农户应收金额比例：80%
    BigDecimal FARMERRECEIVABLEAMOUNT_RATE = BigDecimal.valueOf(0.8);

    /**
     * 国家：中国ID
     */
    Integer CHINA_ID = 45;

    /**
     * Redis、JWT相关配置
     */
    //token请求头 标识
    String AUTH_HEADER = "Authorization";

    //Redis过期时间,默认30分钟
    Long TOKEN_EXPIRED = 30L;

    //JWT生成token密钥
    String JWT_SECRET = "dolaing";

    //JWT过期时间,默认7天
    Long JWT_EXPIRED = 7L * 24 * 60 * 60 * 1000;

}
