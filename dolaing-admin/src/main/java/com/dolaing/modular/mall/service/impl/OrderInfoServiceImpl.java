package com.dolaing.modular.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.core.util.DtoTransUtil;
import com.dolaing.modular.api.base.IResult;
import com.dolaing.modular.api.enums.PayEnum;
import com.dolaing.modular.api.enums.ResultEnum;
import com.dolaing.modular.mall.dao.OrderGoodsMapper;
import com.dolaing.modular.mall.model.OrderGoods;
import com.dolaing.modular.mall.model.OrderInfo;
import com.dolaing.modular.mall.dao.OrderInfoMapper;
import com.dolaing.modular.mall.model.Shop;
import com.dolaing.modular.mall.service.IOrderInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dolaing.modular.mall.vo.OrderGoodsVo;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.member.dao.PayAccountMapper;
import com.dolaing.modular.member.model.UserAccountRecord;
import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.modular.system.model.User;
import com.dolaing.pay.client.entity.zlian.Common2901Result;
import com.dolaing.pay.client.entity.zlian.OnlineDepositShortDTO;
import com.dolaing.pay.client.entity.zlian.WithdrawNoticeDTO;
import com.dolaing.pay.client.enums.PaymentEnum;
import com.dolaing.pay.client.utils.IdUtil;
import com.dolaing.pay.client.utils.PayUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zx
 * @since 2018-08-04
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {
    Logger logger = LoggerFactory.getLogger(OrderInfoServiceImpl.class);
    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private OrderGoodsMapper orderGoodsMapper;


    /**
     * 根据用户查询订单-分页查询
     *
     * @param user
     * @return
     */
    public Page queryOrdersByUser(Page page, User user) {
        Map map = new HashMap();
        map.put("page", page);
        map.put("user", user);
        Integer count = orderInfoMapper.queryOrdersCountByUser(user);
        if (count <= 0) {
            page.setRecords(Collections.emptyList());
        }
        List<OrderInfoVo> orderInfoVos = orderInfoMapper.queryOrdersByUser(map);
        page.setTotal(count);
        page.setRecords(orderInfoVos);
        return page;
    }

    @Override
    public Integer saveOrderInfo(OrderInfo orderInfo) {
        return orderInfoMapper.saveOrderInfo(orderInfo);
    }

    @Override
    public Boolean batchDeliver(String account,String ids) {
        Map map = new HashMap();
        map.put("account",account);
        map.put("ids",ids);
        return orderInfoMapper.batchDeliver(map);
    }

    @Override
    public IResult payOrder(UserPayAccount userPayAccount , String orderId ){
        OrderInfo orderInfo = new OrderInfo(Integer.valueOf(orderId)).selectById();
        if(orderInfo.getPayStatus() == 1){
            return PayEnum.PAIED ;
        }else if(orderInfo.getPayStatus() != 0){
            return PayEnum.SYS_ERR ;
        }
        String merchantSeqId  = IdUtil.randomBase62(32);
        //先支付
        /**
        Map map = transferInOrOutPlatform(userPayAccount ,orderInfo.getGoodsAmount() , merchantSeqId ,1);
        if(!(Boolean) map.get("flag")){
            return PayEnum.SYS_ERR ;
        }

         **/

        //增加支付流水
        UserAccountRecord userAccountRecord = new UserAccountRecord();
        userAccountRecord.setUserId(userPayAccount.getUserId());
        userAccountRecord.setAmount(orderInfo.getGoodsAmount());
        userAccountRecord.setPayment(PaymentEnum.PAY_ZLIAN.getCode());
        userAccountRecord.setRemarks("订单支付");
        userAccountRecord.setProcessType(2);
        userAccountRecord.setStatus(1);
        userAccountRecord.setSourceId(orderInfo.getOrderSn());
        userAccountRecord.setSeqId(merchantSeqId);
        userAccountRecord.insert();

        /**
         * 支付成功之后，将定金打给农户和卖家
         */
        String merchantSeqIdFarmer  = IdUtil.randomBase62(32);
        String merchantSeqIdSeller  = IdUtil.randomBase62(32);


        //查询商品归属农户，因一个订单对应一种商品，所以取任意一条记录
        List<OrderGoodsVo> goodsVos= orderGoodsMapper.queryOrderGoodsByOrderId(orderInfo.getId());
        String farmer = goodsVos.get(0).getFarmerId() ;
        UserPayAccount farmerPayAccount = new UserPayAccount().selectOne(" user_id = {0} " ,farmer);
        //根据店铺信息获取卖家账号
        Shop shop = new Shop().selectById(orderInfo.getShopId()) ;
        String seller = shop.getUserId();
        UserPayAccount sellerPayAccount = new UserPayAccount().selectOne(" user_id = {0} " ,seller);
        //todo 将定金打给农户和卖家
        /**
         * 增加定金打给农户和卖家的流水
         */


        //增加定金打给农户流水
        userAccountRecord.setId(null);
        userAccountRecord.setUserId(farmer);
        //农户应该得到的钱  订单总金额 * 定金比例 * 0.8
        userAccountRecord.setAmount(orderInfo.getGoodsAmount().multiply(goodsVos.get(0).getDepositRatio().multiply(new BigDecimal("0.8"))));
        userAccountRecord.setPayment(PaymentEnum.PAY_ZLIAN.getCode());
        userAccountRecord.setRemarks("由平台转入定金");
        userAccountRecord.setProcessType(2);
        userAccountRecord.setStatus(1);
        userAccountRecord.setSourceId(orderInfo.getOrderSn());
        userAccountRecord.setSeqId(merchantSeqIdFarmer);
        userAccountRecord.insert();

        //增加定金打给卖家流水
        userAccountRecord.setId(null);
        userAccountRecord.setUserId(seller);
        //农户应该得到的钱  订单总金额 * 定金比例 * 0.1
        userAccountRecord.setAmount(orderInfo.getGoodsAmount().multiply(goodsVos.get(0).getDepositRatio().multiply(new BigDecimal("0.1"))));
        userAccountRecord.setPayment(PaymentEnum.PAY_ZLIAN.getCode());
        userAccountRecord.setRemarks("由平台转入定金");
        userAccountRecord.setProcessType(2);
        userAccountRecord.setStatus(1);
        userAccountRecord.setSourceId(orderInfo.getOrderSn());
        userAccountRecord.setSeqId(merchantSeqIdSeller);
        userAccountRecord.insert();

        /**
         * 修改订单信息
         */
        orderInfo.setBuyerMoneyPaid(orderInfo.getGoodsAmount());
        orderInfo.setPayStatus(1);
//        orderInfo.setFarmerMoneyReceived();
        orderInfo.setFarmerReceiveStatus(1);
        orderInfo.setSellerReceiveStatus(1);
        orderInfo.setPaidTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderInfo.updateById();
        return ResultEnum.SUCCESS;
    }

    @Override
    public OrderInfoVo queryOrderById(Integer orderId) {
        return orderInfoMapper.queryOrderById(orderId);
    }


    /**
     * @Author: 张立华
     * @Description: 资金转入或者转出
     * @param amt 金额
     * @param merchantSeqId 流水号
     * @param type 1 资金快捷转入  2 资金转出
     * @return: *
     * @Date: 14:58 2018/6/11
     */
    public Map transferInOrOutPlatform(UserPayAccount userPayAccount , BigDecimal amt, String merchantSeqId , int type ){
        Map result = new HashMap<>();
        result.put("flag",true);
        if(amt.compareTo(new BigDecimal("0")) != 1){
            result.put("flag",false);
            result.put("msg","金额必须大于0元");
            return result;
        }else{
            BigDecimal tempAmt = amt.multiply(new BigDecimal(100));
            if(new BigDecimal(tempAmt.intValue()).compareTo(tempAmt) != 0 ){
                result.put("flag",false);
                result.put("msg","金额小数位不能超过2位");
                return result;
            }
            amt = new BigDecimal(tempAmt.intValue()); //将金额元转成分
        }
            //证联支付充值到账户余额 --对应资金转入接口
            //资金转入
            Map map = new HashMap();
            if(type == 1){
                OnlineDepositShortDTO onlineDepositShortDTO = new OnlineDepositShortDTO();
                //转入信息
                DtoTransUtil.userPayAccountToOnlineDepositShortDto(onlineDepositShortDTO,userPayAccount);
                onlineDepositShortDTO.setTransAmt(amt.toString());
                onlineDepositShortDTO.setMerchantSeqId(merchantSeqId);
                //调用转入接口
                map =  PayUtil.onlineDepositShort(onlineDepositShortDTO);
            }else if(type == 2 ){
                WithdrawNoticeDTO withdrawNoticeDTO = new WithdrawNoticeDTO();
                //转出信息
                DtoTransUtil.userPayAccountToWithdrawNoticeDTO(withdrawNoticeDTO,userPayAccount);
                withdrawNoticeDTO.setTransAmt(amt.toString());
                withdrawNoticeDTO.setMerchantSeqId(merchantSeqId);
                map = PayUtil.withdrawNotice(withdrawNoticeDTO);
            }else{
                result.put("flag",false);
                result.put("msg","参数错误");
                return result;
            }
            Common2901Result common2901Result =
                    JSONObject.toJavaObject((JSONObject)map.get("data"),Common2901Result.class);
            if(common2901Result ==null || !common2901Result.getRespCode().toString().equals("RC00")){
                //验证返回结果是否成功
                logger.error("用户["+userPayAccount.getUserId()+"]资金"+(type==1 ? "转入":"转出")+"异常："+common2901Result.getRespCode());
                result.put("flag",false);
                result.put("msg",common2901Result.getRespDesc());
            }
        return result ;
    }

//    /**
//     * @Author: 张立华
//     * @Description: 充值 userAccountDetailVo 用户id必须传
//     * @params: *
//     * @return: *
//     * @Date: 15:55 2018/5/25
//     */
//    @Transactional
//    public Map transferIn(UserAccountDetailVo userAccountDetailVo,PayPlatformEnum payPlatformEnum) {
//        String seqId = IdUtil.randomBase62(32) ;
//        Map result = transferInOrOutPlatform(userAccountDetailVo.getUserId(),userAccountDetailVo.getAmt(),seqId,1,payPlatformEnum);
//        if(!(Boolean) result.get("flag")){
//            return result ;
//        }
//        //操作余额
//        UserAccount userAccount = new UserAccount();
//        userAccount.setAmount(userAccountDetailVo.getAmt());
//        userAccount.setUserId(userAccountDetailVo.getUserId());
//        iUserAccountDao.optBalance(userAccount);
//        //查询余额
//        userAccount = iUserAccountDao.getAccountByUserId(userAccountDetailVo.getUserId());
//        //生成交易流水
//        UserTransactionRecord userTransactionRecord = new UserTransactionRecord();
//        userTransactionRecord.setAmt(userAccountDetailVo.getAmt());
//        userTransactionRecord.setFromUserId(userAccountDetailVo.getUserId());
//        userTransactionRecord.setToUserId(userAccountDetailVo.getUserId());
//        userTransactionRecord.setSeqId(seqId);
//        userTransactionRecord.setTransInSeqId(seqId);
//        userTransactionRecord.setStatus("100");
//        userTransactionRecord.setType("3");
//        userTransactionRecord.setBalance(userAccount.getAmount());
//        userTransactionRecord.setId(RandomUtil.randomUUID());
//        userTransactionRecord.setCreateDate(new Date());
//        userTransactionRecord.setCreateBy(userAccountDetailVo.getUserId());
//        userTransactionRecord.setUpdateBy(userAccountDetailVo.getUserId());
//        userTransactionRecord.setUpdateDate(new Date());
//        if(payPlatformEnum.equals(PayPlatformEnum.PAY_ZLIAN)){
//            userTransactionRecord.setPlatform("1");
//        }
//        iUserTransactionRecordDao.insert(userTransactionRecord);
//        result.put("flag",true);
//        result.put("msg","");
//        return result;
//    }
//
//
//
//    /**
//     * @Author: 张立华
//     * @Description: 提现
//     * @params: *
//     * @return: *
//     * @Date: 15:55 2018/5/25
//     */
//    public Map transferOut(UserAccountDetailVo userAccountDetailVo,PayPlatformEnum payPlatformEnum) {
//        String seqId = IdUtil.randomBase62(32) ;
//        Map result = transferInOrOutPlatform(userAccountDetailVo.getUserId(),userAccountDetailVo.getAmt(),seqId,2,payPlatformEnum);
//        if(!(Boolean) result.get("flag")){
//            return result ;
//        }
//        //操作余额
//        UserAccount userAccount = new UserAccount();
//        userAccount.setAmount(userAccountDetailVo.getAmt().multiply(new BigDecimal(-1)));
//        userAccount.setUserId(userAccountDetailVo.getUserId());
//        iUserAccountDao.optBalance(userAccount);
//        //查询余额
//        userAccount = iUserAccountDao.getAccountByUserId(userAccountDetailVo.getUserId());
//        //生成交易流水
//        UserTransactionRecord userTransactionRecord = new UserTransactionRecord();
//        userTransactionRecord.setAmt(userAccountDetailVo.getAmt());
//        userTransactionRecord.setFromUserId(userAccountDetailVo.getUserId());
//        userTransactionRecord.setToUserId(userAccountDetailVo.getUserId());
//        userTransactionRecord.setSeqId(seqId);
//        userTransactionRecord.setTransOutSeqId(seqId);
//        userTransactionRecord.setStatus("1"); //提现交易中
//        userTransactionRecord.setType("4");
//        userTransactionRecord.setBalance(userAccount.getAmount());
//        userTransactionRecord.setId(RandomUtil.randomUUID());
//        userTransactionRecord.setCreateDate(new Date());
//        userTransactionRecord.setCreateBy(userAccountDetailVo.getUserId());
//        userTransactionRecord.setUpdateBy(userAccountDetailVo.getUserId());
//        userTransactionRecord.setUpdateDate(new Date());
//        if(payPlatformEnum.equals(PayPlatformEnum.PAY_ZLIAN)){
//            userTransactionRecord.setPlatform("1");
//        }
//        iUserTransactionRecordDao.insert(userTransactionRecord);
//        result.put("flag",true);
//        result.put("msg","");
//        return result;
//    }
//
//    /**
//     * @Author: 张立华
//     * @Description:转账:转出
//     * @params: *
//     * @return: *
//     * @Date: 14:13 2018/5/22synchronized
//     */
//    @Transactional
//    public  Map transferAccount(UserTransactionVo userTransactionVo,PayPlatformEnum payPlatformEnum){
//        /**
//         * 先将付款方的钱转入到平台
//         */
//        String seqId = IdUtil.randomBase62(32) ;
//        String seqInId = IdUtil.randomBase62(32) ;
//        String seqOutId = IdUtil.randomBase62(32) ;
//        Map result = transferInOrOutPlatform(userTransactionVo.getFromUserId(),userTransactionVo.getAmt(),seqId,1,payPlatformEnum);
//        if(!(Boolean) result.get("flag")){
//            return result ;
//        }
//
//        //增加交易流水
//        UserTransactionRecord userTransactionRecord = new UserTransactionRecord();
//        userTransactionRecord.setAmt(userTransactionVo.getAmt());
//        userTransactionRecord.setFromUserId(userTransactionVo.getFromUserId());
//        userTransactionRecord.setSeqId(seqId);
//        userTransactionRecord.setStatus("2");
//        userTransactionRecord.setType("1");
//        userTransactionRecord.setId(RandomUtil.randomUUID());
//        userTransactionRecord.setCreateDate(new Date());
//        userTransactionRecord.setCreateBy(userTransactionVo.getFromUserId());
//        userTransactionRecord.setUpdateBy(userTransactionVo.getFromUserId());
//        userTransactionRecord.setUpdateDate(new Date());
//        userTransactionRecord.setTransInSeqId(seqInId); //资金转入交易流水
//        if(payPlatformEnum.equals(PayPlatformEnum.PAY_ZLIAN)){
//            userTransactionRecord.setPlatform("1");
//        }
//        /**
//         * 再将钱转入到收款方
//         */
//        userTransactionRecord.setTransOutSeqId(seqOutId);
//        result = transferInOrOutPlatform(userTransactionVo.getToUserId(),userTransactionVo.getAmt(),seqOutId,2,payPlatformEnum);
//        if(!(Boolean) result.get("flag")){
//            userTransactionRecord.setStatus("99");
//            userTransactionRecord.setMessage(result.get("msg").toString());
//            iUserTransactionRecordDao.insert(userTransactionRecord);
//            return result ;
//        }
//        userTransactionRecord.setStatus("3"); //资金转出到收款方交易中
//        iUserTransactionRecordDao.insert(userTransactionRecord);
//        logger.debug("新增转账交易记录[编号："+userTransactionRecord.getSeqId()+"]");
//        result.put("flag",true);
//        result.put("msg","");
//        return result;
//    }
//
//
//
//    /**
//     * @Author: 张立华
//     * @Description:转账:转出回执
//     * @params: *
//     * @return: *
//     * @Date: 14:13 2018/5/22
//     */
//    @Transactional
//    public Map transferAccountReceipt(UserTransactionRecord userTransactionRecord,Common2910Result common2910Result){
//        Map result = new HashMap();
//        String status = userTransactionRecord.getStatus() ;
//        String transOutSeqId = userTransactionRecord.getTransOutSeqId() ;
//        //如果是转出的回执
//        if(transOutSeqId.equals(common2910Result.getMerchantSeqId())){
//            if(StringUtils.isNotBlank(status) && status.equals("3") && common2910Result.getRespCode().equals("RC00")){
//                //转出处于交易中状态，且交易回执为已完成交易，则完成转出
//                userTransactionRecord.setStatus("100");
//                iUserTransactionRecordDao.updateByPrimaryKey(userTransactionRecord);
//            }
//        }
//        logger.debug("交易记录[编号："+userTransactionRecord.getSeqId()+"],状态值由"+status+"变更为"+userTransactionRecord.getStatus());
//        result.put("flag",true);
//        result.put("msg","");
//        return result;
//    }


}
