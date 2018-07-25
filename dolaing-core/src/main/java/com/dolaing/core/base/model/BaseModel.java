package com.dolaing.core.base.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: zx
 * Date: Created in 2018/07/03 9:50
 * Copyright: Copyright (c) 2018
 * Description： 基础类
 */
@Data
public abstract class BaseModel<T extends BaseModel> extends Model<T> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;

    /**
     * 创建时间（create_time）
     */
    protected Date createTime;

    /**
     * 创建人,对应account (create_by)
     */
    protected String createBy;

    /**
     * 修改时间 (update_time)
     */
    protected Date updateTime;

    /**
     * 修改人,对应account (update_by)
     */
    protected String updateBy;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
