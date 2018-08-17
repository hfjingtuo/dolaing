package com.dolaing.modular.api.member;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.core.common.annotion.AuthAccess;
import com.dolaing.core.support.HttpKit;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.api.base.Result;
import com.dolaing.modular.mall.vo.UserAccountRecordVo;
import com.dolaing.modular.member.model.UserAccountRecord;
import com.dolaing.modular.member.service.IAccountRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @AuthAccess
    public Result queryRecordsByUser(@RequestParam String userId, @RequestParam Integer pageSize, @RequestParam Integer pageNo) {
        Page page = new UserAccountRecord().selectPage(new Page<UserAccountRecord>(pageNo, pageSize),
                new EntityWrapper<UserAccountRecord>().eq("user_id", userId).orderBy("id", false));
        return render(page);
    }

    @ApiOperation(value = "支付流水详情")
    @GetMapping("/getPayDetail")
    @AuthAccess
    public Object getPayDetail(@RequestParam Integer orderId, @RequestParam Integer processType) {
        String token = JwtTokenUtil.getToken(HttpKit.getRequest());
        String account = JwtTokenUtil.getAccountFromToken(token);
        UserAccountRecordVo userAccountRecordVo = accountRecordService.queryPayDetail(orderId, account, processType);
        return render(userAccountRecordVo);
    }
}
