package com.dolaing.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dolaing.core.datascope.DataScope;
import com.dolaing.modular.system.dao.DictionaryMapper;
import com.dolaing.modular.system.model.Dictionary;
import com.dolaing.modular.system.service.IDictionaryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zx
 * @create 2018-07-02 9:53
 * @desc
 **/
@Service
@Transactional
public class IDictionaryServiceImpl extends ServiceImpl<DictionaryMapper,Dictionary> implements IDictionaryService {

    @Override
    public Dictionary selectOne(Dictionary dictionary) {
        return this.baseMapper.selectOne(dictionary);
    }

    @Override
    public List<Map<String, Object>> list(@Param("dictName") String dictName) {
        return this.baseMapper.list(dictName);
    }

    @Override
    public List<Map<String, Object>> selectDictionarys(DataScope dataScope, String name) {
        return this.baseMapper.selectDictionarys(dataScope, name);
    }



    @Override
    public List<Dictionary> selectByDictName(String dictName) {
        return this.baseMapper.selectByDictName(dictName);
    }

    @Override
    public Dictionary getDictLabel(String dictName, String dictLabel) {
        return this.baseMapper.getDictLabel(dictName, dictLabel);
    }

}