package com.gpm.pay.entity.zlian;

import lombok.Data;

/**
 * @Author:张立华
 * @Description:
 * @Date：Created in 16:00 2018/5/17
 * @Modified By:
 */
@Data
public class ParamsDTO {
    private static final long serialVersionUID = 1L;
    //版本号
    private String verNum ;
    //14位，日期时间戳，其格式为YYYYMMDDHHMMSS
    private String sysDateTime ;
    //9位，机构代码，由证联支付分配的9位定长的唯一代号
    private String instuId ;
    //交易类型
    private String transType ;
    //数字密文包，调用方使用对方公钥加密产生
    private String encMsg ;
    //数字签名，调用方使用己方私钥产生
    private String checkValue ;
}
