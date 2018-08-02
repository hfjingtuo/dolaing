package com.dolaing.modular.api;

import com.dolaing.core.base.controller.BaseController;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.common.constant.JwtConstants;
import com.dolaing.core.shiro.ShiroKit;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.mall.vo.EditPasswordVo;
import com.dolaing.modular.system.model.User;
import com.dolaing.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 修改密码
 *
 * @Author 王柳
 * @Date 2018/8/2 16:17
 */
@RestController
@RequestMapping("/dolaing")
public class EditPasswordApi extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 确认提交修改密码
     *
     * @return
     */
    @PostMapping("/confirmSubmitEditPwd")
    public Object confirmSubmitEditPwd(@RequestBody EditPasswordVo password) {
        String oldPwd = password.getOldPwd();
        String newPwd = password.getNewPwd();
        String rePwd = password.getRePwd();
        if (ToolUtil.isOneEmpty(oldPwd, newPwd, rePwd)) {
            return new ErrorTip(500, "参数有空值");
        }
        String requestHeader = getHeader(JwtConstants.AUTH_HEADER);
        String userId = "";
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            userId = JwtTokenUtil.getUsernameFromToken(requestHeader.substring(7));
        }
        User user = userService.selectById(Integer.valueOf(userId));
        String oldMd5 = ShiroKit.md5(oldPwd, user.getSalt());
        if (!user.getPassword().equals(oldMd5)) {
            return new ErrorTip(501, "原密码不正确");
        }
        if (ToolUtil.isEmpty(newPwd) || newPwd.length() < 6 || newPwd.length() > 20) {
            return new ErrorTip(502, "密码长度为6-20位");
        }
        if (!newPwd.equals(rePwd)) {
            return new ErrorTip(503, "两次密码输入不一致");
        }
        String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
        user.setPassword(newMd5);
        user.updateById();
        return SUCCESS_TIP;
    }
}
