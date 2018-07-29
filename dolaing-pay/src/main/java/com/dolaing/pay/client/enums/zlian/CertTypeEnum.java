package com.dolaing.pay.client.enums.zlian;

import lombok.Getter;

/**
 * @Author:张立华
 * @Description: 证件类型
 * @Date：Created in 18:57 2018/5/11
 * @Modified By:
 */
@Getter
public enum CertTypeEnum {
    NATIONAL_IDENTITY_CARD("00","身份证"),
    PASSPORT("01","护照"),
    MILITARY_ID("02","军官证"),
    SOLDIER_CARD("03","士兵证"),
    REENTRY_PERMIT("04","回乡证"),
    HOUSEHOLD_REGISTER("05","户口本"),
    FOREIGN_PASSPORT("06","外国护照"),
    OTHER("07","其他"),
    TEMPORARY_RESIDENTIAL_PERMIT("08","暂住证"),
    POLICE_OFFICER_CARD("09","警官证"),
    CIVIL_SERVICECARD("10","文职干部证"),
    HONGKONG_MACAO_REENTRY_PERMIT("11","港澳同胞回乡证"),
    ORGANIZATION_CODE_CERTIFICATE("61","组织机构代码证"),
    BUSINESS_LICENCE("62","营业执照"),
    TAX_REGISTRATION_CERTIFICATE("63","税务登记证"),
    OTHER_ORGANIZATION_CARD("69","其他机构证件类型");

    private String code ;
    private String name ;

    CertTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
