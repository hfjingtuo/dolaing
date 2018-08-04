package com.dolaing.modular.api.member;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.api.base.Result;
import com.dolaing.modular.member.model.UserAccountRecord;
import com.dolaing.modular.member.service.IAccountRecordService;
import com.dolaing.modular.system.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:张立华
 * @Description: 订单记录
 * @Date：Created in 19:21 2018/8/4
 * @Modified By:
 */
@RestController
@RequestMapping("/dolaing/orderRecord")
public class OrderRecordApi extends BaseApi {
    @Autowired
    private IAccountRecordService accountRecordService;

    @ApiOperation(value = "订单查询")
    @RequestMapping("/queryRecordsByUser")
    public Result queryRecordsByUser(@RequestParam String userId, @RequestParam Integer pageSize, @RequestParam Integer pageNo){
        User user = new User().selectOne("account = {0}" , userId);
        if(user != null ){
            //todo 判断农户、商家还是买家
        }
        Page page = new UserAccountRecord().selectPage(new Page<UserAccountRecord>(pageNo,pageSize),
                new EntityWrapper<UserAccountRecord>().eq("user_id",userId).orderBy("id",false));
        return render(page);
    }
}
