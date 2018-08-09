package com.dolaing.modular.api;

import com.dolaing.core.common.constant.GlobalData;
import com.dolaing.modular.api.base.BaseApi;
import org.springframework.web.bind.annotation.*;

/**
 * Author: zx
 * Date: Created in 2018/08/08 10:13
 * Copyright: Copyright (c) 2018
 * Description： 数据字典接口类
 */
@RestController
@RequestMapping("/dolaing")
public class DictionaryApi extends BaseApi {

    /**
     * 根据dictName查找对应值
     */
    @PostMapping("/getDictionary")
    public Object getAreaList(@RequestParam String dictName) {
        return render(GlobalData.DICTIONARY_ARR.get(dictName));
    }
}
