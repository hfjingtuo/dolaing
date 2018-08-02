package com.dolaing.modular.mall.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码VO类
 *
 * @Author 王柳
 * @Date 2018/8/2 17:24
 */
@Data
public class EditPasswordVo extends Model<EditPasswordVo> {
    private String oldPwd; //原密码
    private String newPwd; //新密码
    private String rePwd; // 确认密码

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
