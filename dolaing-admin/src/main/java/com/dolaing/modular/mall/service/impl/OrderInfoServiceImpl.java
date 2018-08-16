package com.dolaing.modular.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.core.util.DateUtils;
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
import com.dolaing.pay.client.entity.zlian.*;
import com.dolaing.pay.client.enums.PaymentEnum;
import com.dolaing.pay.client.enums.zlian.StatusTradeTypeEnum;
import com.dolaing.pay.client.enums.zlian.TiedCardTypeEnum;
import com.dolaing.pay.client.utils.IdUtil;
import com.dolaing.pay.client.utils.PayUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
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

    /**
     * 批量收货
     * @param account
     * @param ids
     * @return
     */
    @Override
    public Boolean batchReceive(String account,String ids) {
        Map map = new HashMap();
        map.put("account",account);
        map.put("ids",ids);
        orderInfoMapper.batchReceive(map);
        return null ;
    }

    /**
     * 订单支付，买家支付给平台
     * @param userPayAccount
     * @param orderId
     * @return
     */
    @Override
    public Map payOrder(UserPayAccount userPayAccount , String orderId ) {
        Map map = new HashMap();
        map.put("code","1000");
        map.put("msg","");
        OrderInfo orderInfo = new OrderInfo(Integer.valueOf(orderId)).selectById();
        if (orderInfo.getPayStatus() == 1) {
            map.put("code",PayEnum.PAIED.getCode());
            map.put("msg",PayEnum.PAIED.getMessage());
            return map;
        } else if (orderInfo.getPayStatus() != 0) {
            map.put("code",PayEnum.NOT_PAY_STATUS.getCode());
            map.put("msg",PayEnum.NOT_PAY_STATUS.getMessage());
            return map;
        }
        String merchantSeqId = IdUtil.randomBase62(32);
        //先支付
        Map mapResult = transferInOrOutPlatform(userPayAccount, orderInfo.getGoodsAmount(), merchantSeqId, 1);
        if (!(Boolean) mapResult.get("flag")) {
            map.put("code",PayEnum.SYS_ERR.getCode());
            map.put("msg",mapResult.get("msg"));
            return map;
        }
        //增加支付流水
        UserAccountRecord userAccountRecord = new UserAccountRecord();
        userAccountRecord.setUserId(userPayAccount.getUserId());
        userAccountRecord.setAmount(orderInfo.getGoodsAmount());
        userAccountRecord.setPaymentId(PaymentEnum.PAY_ZLIAN.getCode());
        userAccountRecord.setRemarks("订单支付");
        userAccountRecord.setProcessType(0);
        userAccountRecord.setStatus(1); //根据返回的结果判断
        userAccountRecord.setSourceId(orderInfo.getOrderSn());
        userAccountRecord.setSeqId(merchantSeqId);
        userAccountRecord.insert();

        /**
         * 修改订单信息
         */
        orderInfo.setBuyerMoneyPaid(orderInfo.getGoodsAmount());
        orderInfo.setPayStatus(1);
        orderInfo.setPaidTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderInfo.updateById();
        return map;
    }

    @Override
    public OrderInfoVo queryOrderById(Integer orderId) {
        return orderInfoMapper.queryOrderById(orderId);
    }




    /**
     * @Author: 张立华
     * @Description: 支付定金或者尾款（待优化）
     * @params: *
     * @return: *
     * @Date: 22:52 2018/8/9
     */
    @Override
    @Async
    public void payOrderDepositOrBalance(Integer opType , Integer roleType ,OrderInfo orderInfo ){
        System.out.println("==opType=>"+opType+"==roleType=>"+roleType);
        String merchantSeqId = IdUtil.randomBase62(32);
        List<OrderGoodsVo> goodsVos = orderGoodsMapper.queryOrderGoodsByOrderId(orderInfo.getId());
        OrderGoodsVo goodsVo = goodsVos.get(0);
        if(roleType == 1){ //如果是卖家
            System.out.println("=====>卖家");
            //根据店铺信息获取卖家账号
            Shop shop = new Shop().selectById(orderInfo.getShopId()) ;
            String sellerId = shop.getUserId();
            UserPayAccount sellerPayAccount = new UserPayAccount().selectOne(" user_id = {0} " ,sellerId);
            BigDecimal amount ;
            if(opType == 1){ //定金金额
                amount = orderInfo.getSellerReceivableAmount().multiply(goodsVo.getDepositRatio());
            }else { //尾款
                amount = orderInfo.getSellerReceivableAmount().subtract(orderInfo.getSellerMoneyReceived());
            }
            System.out.println("=====>卖家金额："+amount.toString());
            /**
             * 判断是否开户了
             */
            if(sellerPayAccount == null ){
                //未开户
                //增加交易流水
                UserAccountRecord userAccountRecord = new UserAccountRecord();
                //增加定金打给农户流水
                userAccountRecord.setUserId(sellerId) ;
                userAccountRecord.setAmount(amount);
                userAccountRecord.setPaymentId(PaymentEnum.PAY_ZLIAN.getCode());
                userAccountRecord.setRemarks(opType == 1 ? "由平台转入定金" : "由平台转入尾款");
                userAccountRecord.setProcessType(opType == 1 ? 1 : 2) ;
                userAccountRecord.setStatus(2);
                userAccountRecord.setErrorMessage("没有开户");
                userAccountRecord.setSourceId(orderInfo.getOrderSn());
                userAccountRecord.setSeqId(merchantSeqId);
                userAccountRecord.insert();
                return ;
            }
            //将尾款打给农户和卖家
            Map sellerMap = transferInOrOutPlatform(sellerPayAccount , amount , merchantSeqId ,2);
            //更改订单状态
            int sellerStatus = 0 ;
            if((Boolean) sellerMap.get("flag")){
                sellerStatus = opType == 1 ? 1 : 3 ; //定金收款中1，尾款收款中3
                orderInfo.setSellerMoneyReceived(orderInfo.getSellerMoneyReceived().add(amount));
                orderInfo.setSellerReceiveStatus(sellerStatus);
                orderInfo.setUpdateTime(new Date());
                orderInfo.updateById();
                //增加交易流水
                UserAccountRecord userAccountRecord = new UserAccountRecord();
                //增加定金打给农户流水
                userAccountRecord.setUserId(sellerId) ;
                userAccountRecord.setAmount(amount);
                userAccountRecord.setPaymentId(PaymentEnum.PAY_ZLIAN.getCode());
                userAccountRecord.setRemarks(opType == 1 ? "由平台转入定金" : "由平台转入尾款");
                userAccountRecord.setProcessType(opType == 1 ? 1 :2) ;
                userAccountRecord.setStatus(0);
                userAccountRecord.setSourceId(orderInfo.getOrderSn());
                userAccountRecord.setSeqId(merchantSeqId);
                userAccountRecord.insert();
            }
        }else if(roleType == 2){
            //查询商品归属农户，因一个订单对应一种商品，所以取任意一条记录
            String farmerId = goodsVo.getFarmerId();
            UserPayAccount farmerPayAccount = new UserPayAccount().selectOne(" user_id = {0} " ,farmerId);
            BigDecimal amount ;
            if(opType == 1){ //定金金额
                amount = orderInfo.getFarmerReceivableAmount().multiply(goodsVo.getDepositRatio());
            }else if(opType == 2){ //尾款金额
                amount = orderInfo.getFarmerReceivableAmount().subtract(orderInfo.getFarmerMoneyReceived());
            }else {
                return ;
            }

            /**
             * 判断是否开户了
             */
            if(farmerPayAccount == null ){
                //未开户
                //增加交易流水
                UserAccountRecord userAccountRecord = new UserAccountRecord();
                //增加定金打给农户流水
                userAccountRecord.setUserId(farmerId) ;
                userAccountRecord.setAmount(amount);
                userAccountRecord.setPaymentId(PaymentEnum.PAY_ZLIAN.getCode());
                userAccountRecord.setRemarks(opType == 1 ? "由平台转入定金" : "由平台转入尾款");
                userAccountRecord.setProcessType(opType == 1 ? 1 :2) ;
                userAccountRecord.setStatus(2);
                userAccountRecord.setErrorMessage("没有开户");
                userAccountRecord.setSourceId(orderInfo.getOrderSn());
                userAccountRecord.setSeqId(merchantSeqId);
                userAccountRecord.insert();
                return ;
            }

            Map farmerMap = transferInOrOutPlatform(farmerPayAccount , amount , merchantSeqId ,2);
            int farmerStatus = 0 ;
            if((Boolean) farmerMap.get("flag")){
                farmerStatus = opType == 1 ? 1 : 3 ; //定金收款中1，尾款收款中3
                orderInfo.setSellerMoneyReceived(orderInfo.getSellerMoneyReceived().add(amount));
                orderInfo.setSellerReceiveStatus(farmerStatus);
                orderInfo.setUpdateTime(new Date());
                orderInfo.updateById();

                //增加交易流水
                UserAccountRecord userAccountRecord = new UserAccountRecord();
                //增加定金打给农户流水
                userAccountRecord.setUserId(farmerId) ;
                userAccountRecord.setAmount(amount);
                userAccountRecord.setPaymentId(PaymentEnum.PAY_ZLIAN.getCode());
                userAccountRecord.setRemarks(opType == 1 ? "由平台转入定金" : "由平台转入尾款");
                userAccountRecord.setProcessType(opType == 1 ? 1 :2) ;
                userAccountRecord.setStatus(0);
                userAccountRecord.setSourceId(orderInfo.getOrderSn());
                userAccountRecord.setSeqId(merchantSeqId);
                userAccountRecord.insert();
            }
        }

    }

    /**
     * 订单状态查询
     * @param userAccountRecord
     */
    @Override
    @Async
    @Transactional
    public void queryOrderTransStatusTask(UserAccountRecord userAccountRecord){
        TranStatusDTO tranStatusDTO  = new TranStatusDTO();
        tranStatusDTO.setMerchantLiqDate(DateUtils.formatDateTime(userAccountRecord.getCreateTime()));
        tranStatusDTO.setTradeType(StatusTradeTypeEnum.TRANS_OUT.getCode());
        tranStatusDTO.setTiedCardType(TiedCardTypeEnum.QUICK.getCode());
        tranStatusDTO.setMerchantSeqId(userAccountRecord.getSeqId());
        Map map = PayUtil.tranStatusQuery(tranStatusDTO);
        Common2404Result common2404Result =
                JSONObject.toJavaObject((JSONObject)map.get("data"),Common2404Result.class);
        logger.debug("订单号"+userAccountRecord.getSourceId()+"证联支付流水号["+userAccountRecord.getSeqId()+"],状态查询结果："+common2404Result.getRespCode()+"["+common2404Result.getRespDesc()+"]");
        if(common2404Result.getRespCode().equals("RC00")){
            //如果交易完成 记录已完成
            userAccountRecord.setStatus(1);
            userAccountRecord.setErrorCode(common2404Result.getRespCode());
            userAccountRecord.setErrorMessage(common2404Result.getRespDesc());
            userAccountRecord.setUpdateTime(DateUtils.getNow());
            userAccountRecord.updateById();
            //修改原订单状态
            OrderInfo orderInfo = new OrderInfo().selectOne("order_sn = {0} and order_status = {1}" ,userAccountRecord.getSourceId() , 1);
            User user = new User().selectOne("account = {0} " ,userAccountRecord.getUserId() ) ;
            if(userAccountRecord.getProcessType() == 1 ){ //定金转入
                if(user.getType().equals(2)){ //卖家
                    orderInfo.setSellerReceiveStatus(2); //定金已到账
                }else if(user.getType().equals(3)){ //农户
                    orderInfo.setFarmerReceiveStatus(2); //定金已到账
                }
            }else if(userAccountRecord.getProcessType() == 2 ){ //尾款转入
                if(user.getType().equals(2)){ //卖家
                    orderInfo.setSellerReceiveStatus(4); //尾款已到账
                }else if(user.getType().equals(3)){ //农户
                    orderInfo.setFarmerReceiveStatus(4); //尾款已到账
                }
            }
            orderInfo.updateById();
        }else{
            userAccountRecord.setErrorCode(common2404Result.getRespCode());
            userAccountRecord.setErrorMessage(common2404Result.getRespDesc());
            userAccountRecord.updateById();
        }
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
            logger.error("用户["+userPayAccount.getUserId()+"]资金"+(type==1 ? "转入":"转出")+"异常："+common2901Result == null ? "支付通讯异常" :common2901Result.getRespCode());
            result.put("flag",false);
            result.put("msg",common2901Result == null ? "支付通讯异常" :common2901Result.getRespDesc());
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



}
