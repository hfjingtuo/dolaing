package com.dolaing.modular.mall.model;

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
 * 店铺
 * </p>
 *
 * @author zhanglihua
 * @since 2018-08-06
 */
@TableName("mall_shop")
@Data
public class Shop extends BaseModel<Shop> {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 店铺名称
     */
    @TableField("shop_name")
    private String shopName;
    /**
     * 店铺拥有者
     */
    @TableField("user_id")
    private String userId;
    /**
     * 经营范围
     */
    @TableField("business_scope")
    private String businessScope;
    /**
     * 国家
     */
    private Integer country;
    /**
     * 省份
     */
    private Integer province;
    /**
     * 城市
     */
    private Integer city;
    /**
     * 品牌名称
     */
    @TableField("brand_name")
    private String brandName;
    /**
     * 品牌id(暂时写死)
     */
    @TableField("brand_id")
    private Integer brandId;

}
