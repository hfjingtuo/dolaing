package com.dolaing.modular.system.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.dolaing.core.base.model.BaseModel;
import lombok.Data;

/**
 * @author Administrator
 * @create 2018-07-02 9:16
 * @desc 数据字典实体
 **/

@Data
@TableName("sys_dictionary")
public class Dictionary extends BaseModel<Dictionary> {

    private static final long serialVersionUID = 1L;

    //参数名称
    private String dictName;

    //参数标签
    private String dictLabel;

    //参数标签
    private String dictEnLabel;

    //参数值
    private String dictValue;

    //备注
    private String remarks;

}
