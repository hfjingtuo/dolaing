package com.dolaing;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.dolaing.core.common.constant.GlobalData;
import com.dolaing.modular.system.model.Area;
import com.dolaing.modular.system.model.Dictionary;
import com.dolaing.modular.system.service.IAreaService;
import com.dolaing.modular.system.service.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: zx
 * Date: Created in 2018/07/05 20:53
 * Copyright: Copyright (c) 2018
 * Description： 启动加载数据
 */
@Component
@Order(value = 1)
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private IDictionaryService dictionaryService;
    @Autowired
    private IAreaService areaService;

    @Override
    public void run(String... args) {
        initDictionarys();
        initAreas();
    }

    /**
     * 初始化数据字典
     */
    private void initDictionarys() {
        List<Map<String, Object>> list = dictionaryService.list(null);
        for (Map<String, Object> map : list) {
            GlobalData.DICTIONARYS.put(map.get("dictName").toString() + "|" + map.get("dictValue").toString(), map.get("dictLabel").toString());
        }
        //数据字典分类
        Dictionary dictionary = new Dictionary();
        List<Dictionary> dictionarys = dictionary.selectAll();

        dictionarys.forEach(obj -> {
            if (GlobalData.DICTIONARY_ARR.containsKey(obj.getDictName())) {
                GlobalData.DICTIONARY_ARR.get(obj.getDictName()).add(obj);
            } else {
                List<Dictionary> arr = new ArrayList<>();
                arr.add(obj);
                GlobalData.DICTIONARY_ARR.put(obj.getDictName(), arr);
            }
        });
        System.out.println(">>>>>>>>>>>>>>>数据字典初始化完成<<<<<<<<<<<<<");
    }

    /**
     * 初始化行政区域
     */
    private void initAreas() {
        Wrapper<Area> wrapper = new EntityWrapper<>();
        List<Area> list = areaService.selectList(wrapper);

        for (Area area : list) {
            GlobalData.AREAS.put(area.getId(), area);
        }
        System.out.println(">>>>>>>>>>>>>>>行政区域初始化完成<<<<<<<<<<<<<");
    }

    /**
     * 刷新全局对象中的数据字典
     */
    public void flushDictionary() {
        GlobalData.DICTIONARYS.clear();
        //initDictionarys();
        GlobalData.DICTIONARY_ARR.clear();
    }
}
