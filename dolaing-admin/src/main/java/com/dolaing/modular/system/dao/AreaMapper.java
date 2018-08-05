package com.dolaing.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dolaing.modular.system.model.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 世界行政区域划分表 Mapper 接口
 * </p>
 *
 * @author zx
 * @since 2018-07-05
 */
public interface AreaMapper extends BaseMapper<Area> {

    /**
     * 查询所有parentID为0和45的子级ID
     */
    List<String> selectParentIds();

    /**
     * 根据 parentId 查询子级区域
     * @param parentId
     * @return
     */
    List<Area> findByParentId(@Param("parentId") String parentId);

}
