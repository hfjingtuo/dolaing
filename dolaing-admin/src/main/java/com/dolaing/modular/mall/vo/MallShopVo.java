package com.dolaing.modular.mall.vo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.dolaing.core.base.model.BaseModel;
import com.dolaing.core.common.constant.GlobalData;
import com.dolaing.modular.mall.model.MallShop;
import lombok.Data;

/**
 * Author: zx
 * Date: Created in 2018/07/26 18:15
 * Copyright: Copyright (c) 2018
 * Description： 店铺
 */
@Data
@TableName("mall_shop")
public class MallShopVo extends BaseModel<MallShopVo> {

    private String shopName; //店铺名称
    private String userId;// 店铺拥有者
    private Integer brandId;// 品牌id
    private String brandName;// 品牌名
    private String businessScope;//经营范围
    private Integer country;//国家
    private Integer province;//省份
    private Integer city;//城市
    private String  provinceLabel ;
    private String cityLabel ;

    public MallShopVo() {
    }

    public MallShopVo(MallShop shop) {
        this.shopName = shop.getShopName();
        this.userId = shop.getUserId() ;
        this.brandId = shop.getBrandId() ;
        this.brandName = shop.getBrandName() ;
        this.businessScope = shop.getBusinessScope();
        this.country = shop.getCountry() ;
        this.province = shop.getProvince() ;
        this.city = shop.getCity() ;
        this.provinceLabel = GlobalData.AREAS.get(this.province).getChName();
        this.cityLabel = GlobalData.AREAS.get(this.city).getChName();
    }
}
