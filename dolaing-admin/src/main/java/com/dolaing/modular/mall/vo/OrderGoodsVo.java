package com.dolaing.modular.mall.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 21:03 2018/8/4
 * @Modified By:
 */
@Data
public class OrderGoodsVo {

    private Integer id ;

    /**
     * 订单号
     */
    private Integer orderId;
    /**
     * 订单商品信息id
     */
    private Integer goodsId;
    /**
     * 商品的名称
     */
    private String goodsName;
    /**
     * 商品的唯一货号
     */
    private String goodsSn;
    /**
     * 商品的购买数量
     */
    private Integer goodsNumber;
    /**
     * 商品的售价
     */
    private BigDecimal goodsPrice;
    /**
     * 品牌id
     */
    private String brandId ;

    /**
     * 品牌名
     */
    private String brandName ;



    /**
     * 土地编号
     */
    private String landSn ;
    /**
     * 农户account
     */
    private String farmerId ;
    /**
     * 预计发货时间
     */
    private String expectDeliverTime ;

    /**
     * 定金比例
     */
    private BigDecimal depositRatio ;
    private BigDecimal landPartArea;//每单位面积
    private String landPartAreaUnit;//每单位面积单位(默认亩)
    private BigDecimal expectPartOutput;//预计单位产量
    private String expectPartOutputUnit;//预计单位产量单位(默认KG)
    private Integer isFreeShipping;//是否包邮(0不包邮 1包邮)
    private String goodsMasterImgs;//产品主图([xx]号分隔)
    //需计算值

    private BigDecimal goodsAmount ;  //商品总金额
    private String goodsMasterImg;//产品主图第一张
    private BigDecimal buyLandArea;//认购面积


    public BigDecimal getBuyLandArea() {
        this.buyLandArea = this.landPartArea.multiply(new BigDecimal(this.goodsNumber));
        return buyLandArea;
    }


    public BigDecimal getGoodsAmount() {
        this.goodsAmount = this.goodsPrice.multiply(new BigDecimal(this.goodsNumber));
        return goodsAmount;
    }

    public String getGoodsMasterImg() {
        if(this.goodsMasterImgs !=null ){
            this.goodsMasterImg = this.goodsMasterImgs.split(",")[0];
        }
        return goodsMasterImg;
    }
}
