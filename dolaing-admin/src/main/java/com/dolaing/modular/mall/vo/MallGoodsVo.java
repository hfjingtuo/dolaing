package com.dolaing.modular.mall.vo;

import com.dolaing.core.base.model.BaseModel;
import com.dolaing.core.util.DateUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: zx
 * Date: Created in 2018/07/25 11:44
 * Copyright: Copyright (c) 2018
 * Description： 商品
 */
@Data
public class MallGoodsVo extends BaseModel<MallGoodsVo> {
    private String goodsSn;//商品的唯一货号
    private String goodsName; //商品的名称
    private BigDecimal shopPrice;// 商品单价
    private String goodsBrief;//商品的简短描述
    private String goodsMasterImgs;//产品主图([xx]号分隔)
    private String depositRatio;// 定金比例
    private Integer isFreeShipping;//是否包邮(0不包邮 1包邮)
    private String brandId;// 品牌
    private String brandName;// 品牌名
    private String catId;//品类
    private String breeds;//品种
    private Date startSubscribeTime;//开始认购时限
    private Date endSubscribeTime;//结束认购时限
    private String shopId;//所属店铺id
    private String farmerId;//农户id

}
