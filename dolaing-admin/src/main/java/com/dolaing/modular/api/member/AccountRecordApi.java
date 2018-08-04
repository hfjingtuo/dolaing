package com.dolaing.modular.api.member;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.api.base.Result;
import com.dolaing.modular.member.model.UserAccountRecord;
import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.modular.member.service.IAccountRecordService;
import com.dolaing.modular.member.service.IPayAccountService;
import com.dolaing.pay.client.entity.zlian.MarginRegisterDTO;
import com.dolaing.pay.client.entity.zlian.MarginSmsDTO;
import com.dolaing.pay.client.enums.PaymentEnum;
import com.dolaing.pay.client.utils.IdUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @Author:zhanglihua
 * @Description: 开户api
 * @Date：Created in 11:33 2018/7/29
 * @Modified By:
 */
@RestController
@RequestMapping("/dolaing/accountRecord")
public class AccountRecordApi extends BaseApi {
    @Autowired
    private IAccountRecordService accountRecordService;

    @ApiOperation(value = "交易记录查询")
    @RequestMapping("/queryRecordsByUser")
    public Result queryRecordsByUser(@RequestParam String userId,@RequestParam Integer pageSize,@RequestParam Integer pageNo){
        Page page = new UserAccountRecord().selectPage(new Page<UserAccountRecord>(pageNo,pageSize),
                new EntityWrapper<UserAccountRecord>().eq("user_id",userId).orderBy("id",false));
        return render(page);
    }
}
