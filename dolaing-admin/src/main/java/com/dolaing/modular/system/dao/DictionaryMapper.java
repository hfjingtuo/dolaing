package com.dolaing.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dolaing.core.datascope.DataScope;
import com.dolaing.modular.system.model.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @create 2018-07-02 10:08
 * @desc
 **/
public interface DictionaryMapper extends BaseMapper<Dictionary> {

    /**
     *根据条件查询字典列表
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
