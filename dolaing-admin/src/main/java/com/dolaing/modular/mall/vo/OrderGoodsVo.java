package com.dolaing.modular.mall.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

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
    private Date expectDeliverTime ;

    /**
     * 开始认购时限
     */
    private Date startSubscribeTime;

    /**
     * 结束认购时限
     */
    private Date endSubscribeTime;

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
    private String landPartAreaUnitName ; //单位面积名称
    private String expectPartOutputUnitName;//预计单位产量单位(默认KG)
    private String depositRatioLabel ; //显示百分比的方式
    private BigDecimal expectPartOutputOrder ;  //此单预计产量
    private BigDecimal depositPayment ;// 定金
    private BigDecimal balancePayment ;// 尾款

    public BigDecimal getBuyLandArea() {
        this.buyLandArea = this.landPartArea.multiply(new BigDecimal(String.valueOf(this.goodsNumber)));
        return buyLandArea;
    }

    public BigDecimal getExpectPartOutputOrder() {
        this.expectPartOutputOrder = this.expectPartOutput.multiply(new BigDecimal(String.valueOf(this.goodsNumber)));
        return expectPartOutputOrder;
    }

    public BigDecimal getGoodsAmount() {
        this.goodsAmount = this.goodsPrice.multiply(new BigDecimal(String.valueOf(this.goodsNumber)));
        return goodsAmount;
    }

    public String getGoodsMasterImg() {
        if(this.goodsMasterImgs !=null ){
            this.goodsMasterImg = this.goodsMasterImgs.split(",")[0];
        }
        return goodsMasterImg;
    }

    public String getLandPartAreaUnitName() {
        return "亩"; //暂时写死
    }

    public String getExpectPartOutputUnitName() {
        return "kg"; //暂时写死
    }

    public String getDepositRatioLabel() {
        return  new DecimalFormat("0.00%").format(this.depositRatio);
    }

    public BigDecimal getDepositPayment() {
        this.depositPayment  = this.goodsPrice.multiply(new BigDecimal(String.valueOf(this.goodsNumber))).multiply(this.depositRatio);
        return depositPayment;
    }

    public BigDecimal getBalancePayment() {
        this.balancePayment  = this.goodsPrice.multiply(new BigDecimal(String.valueOf(this.goodsNumber))).multiply(new BigDecimal("1").subtract(this.depositRatio));
        return balancePayment;
    }
}
