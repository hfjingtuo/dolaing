package com.dolaing.modular.system.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.dolaing.core.base.model.BaseModel;
import lombok.Data;

/**
 * <p>
 * 世界行政区域划分表
 * </p>
 * @author zx
 * @since 2018-07-05
 */
@Data
@TableName("sys_area")
public class Area extends BaseModel<Area> {

    private static final long serialVersionUID = 1L;

    /**
     * 中文名称
     */
    private String chName;

    /**
     * 父节点
     */
    private String parentId;

}
