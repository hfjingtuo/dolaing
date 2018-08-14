package com.dolaing.core.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.dolaing.config.properties.SMSProperties;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: zx
 * Date: Created in 2018/07/10 17:51
 * Copyright: Copyright (c) 2018
 * Description： 注册验证码工具类
 */
public class RegisterCodeUtil {

    static SMSProperties smsProperties = SpringContextHolder.getBean(SMSProperties.class);

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    static final String accessKeyId = smsProperties.getAccessKeyId();
    static final String accessKeySecret = smsProperties.getAccessKeySecret();

    /**
     * 发送短信
     * @param phone
     * @return
     */
    public static Boolean sendMsg(String phone,String code) {
        try {
            //设置超时时间-可自行调整
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(smsProperties.getSignName());
            //必填:短信模板-可在短信控制台中找到
            //【都来应】您的验证码782914，该验证码5分钟内有效，请在页面填写完成验证，勿泄漏于他人！
            request.setTemplateCode(smsProperties.getTemplateCode());
            request.setTemplateParam("{\"code\":\"" + code + "\"}");
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("yourOutId");
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

            if (sendSmsResponse != null) {
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<短信发送接口返回数据>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println("sendSmsResponseCode=" + sendSmsResponse.getCode());
                System.out.println("sendSmsResponseMessage" + sendSmsResponse.getMessage());
                //请求成功
                if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<短信发送成功>>>>>>>>>>>>>>>>>>>>>>>>");
                    return true;
                }
            }
        } catch (ClientException e) {
            System.err.println("发送短信异常" + e.getMessage());
            return false;
        }
        return false;
    }

    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

    /**
     * 生成6位随机数
     * @return
     */
    public static String randomCode() {
        String code = "";
        while (code.length() < 6) {
            code += (int) (Math.random() * 10);// 生成6位随机数
        }
        return code;
    }
}
