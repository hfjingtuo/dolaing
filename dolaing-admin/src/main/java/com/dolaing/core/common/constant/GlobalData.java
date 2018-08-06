package com.dolaing.core.common.constant;

import com.dolaing.modular.system.model.Area;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: zx
 * Date: Created in 2018/07/06 9:30
 * Copyright: Copyright (c) 2018
 * Description： 系统全局数据
 */
public class GlobalData {

    /**
     * 保存系统配置字典值
     */
    public static Map<String, String> DICTIONARYS = new HashMap<>();

    /**
     * 保存行政区域
     */
    public static Map<Integer, Area> AREAS = new HashMap<>();
    /**
     * 保存系统配置字典值 key为dictname ,value 为 dict集合
     */
    public static Map<String, List> DICTIONARY_ARR = new HashMap<>();
}
