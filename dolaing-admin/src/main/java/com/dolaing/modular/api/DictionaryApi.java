package com.dolaing.modular.api;

import com.dolaing.core.common.constant.GlobalData;
import org.springframework.web.bind.annotation.*;

/**
 * Author: zx
 * Date: Created in 2018/08/08 10:13
 * Copyright: Copyright (c) 2018
 * Description： TODO
 */
@RestController
@RequestMapping("/dolaing")
public class DictionaryApi {

    /**
     * 根据dictName查找对应值
     */
    @GetMapping("/getDictionary/{dictName}")
    public Object getAreaList(@PathVariable String dictName) {
        return GlobalData.DICTIONARY_ARR.get(dictName);
    }
}
