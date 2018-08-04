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
    /**
     * 订单商品信息id
     */
    private Integer id;
    /**
     * 订单号
     */
    private Integer orderId;
    /**
     * 商品的的id
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
     * 预计单位产量
     */
    private String expectPartOutput ;
    /**
     * 商品总金额
     */
    private String goodsAmount ;
    /**
     * 定金比例
     */
    private String depositRatio ;

}
