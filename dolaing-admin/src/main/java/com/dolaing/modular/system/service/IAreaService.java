package com.dolaing.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.dolaing.modular.system.model.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 世界行政区域划分 业务类
 * </p>
 *
 * @author zx
 * @since 2018-07-05
 */
public interface IAreaService extends IService<Area> {

    /**
     * 根据 parentId 查询子级区域
     * @param parentId
     * @return
     */
    List<Area> findByParentId(@Param("parentId") String parentId);
}
