package com.dolaing.modular.system.warpper;

import com.dolaing.core.common.constant.GlobalData;
import com.dolaing.core.common.constant.factory.ConstantFactory;
import com.dolaing.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 *
 * @author zx
 * @date 2018年2月13日 下午10:47:03
 */
public class UserWarpper extends BaseControllerWarpper {

    public UserWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("typeName", ConstantFactory.me().getUserTypeName((String) map.get("type")));
        map.put("sexName", map.get("sex") != null ? GlobalData.DICTIONARYS.get("sex|" + map.get("sex")) : "");
        map.put("roleName", map.get("role_id") != null ? ConstantFactory.me().getRoleName(map.get("role_id").toString()) : "");
        map.put("statusName", map.get("status") != null ? ConstantFactory.me().getStatusName((Integer) map.get("status")) : "");

    }

}
