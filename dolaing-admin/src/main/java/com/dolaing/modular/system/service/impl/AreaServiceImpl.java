package com.dolaing.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dolaing.modular.system.dao.AreaMapper;
import com.dolaing.modular.system.model.Area;
import com.dolaing.modular.system.service.IAreaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 世界行政区域划分表 服务实现类
 * </p>
 *
 * @author zx
 * @since 2018-07-05
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

    @Override
    public List<String> selectParentIds() {
        return this.baseMapper.selectParentIds();
    }

    @Override
    public List<Area> findByParentId(String parentId) {
        return this.baseMapper.findByParentId(parentId);
    }
}
