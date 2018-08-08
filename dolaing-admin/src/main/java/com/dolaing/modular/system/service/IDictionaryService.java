package com.dolaing.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.dolaing.core.datascope.DataScope;
import com.dolaing.modular.system.model.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zx
 * @create 2018-07-02 9:48
 * @desc
 **/
public interface IDictionaryService extends IService<Dictionary> {

    /**
     * 查询一个字典
     */
    Dictionary selectOne(Dictionary dictionary);

    /**
     * 查询字典列表
     */
    List<Map<String, Object>> list(@Param("dictName") String dictName);

    List<Map<String, Object>> selectDictionarys(@Param("dataScope") DataScope dataScope,
                                                @Param("name") String name);

    /**
     * 根据dictName查询字典列表
     */
    List<Dictionary> selectByDictName(@Param("dictName") String dictName);

    /**
     * 查询标签
     * @param dictName
     * @param dictLabel
     * @return
     */
    Dictionary getDictLabel(@Param("dictName") String dictName, @Param("dictLabel") String dictLabel);

}
