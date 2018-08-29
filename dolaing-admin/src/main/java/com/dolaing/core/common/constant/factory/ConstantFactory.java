package com.dolaing.core.common.constant.factory;

import com.dolaing.core.common.constant.cache.Cache;
import com.dolaing.core.common.constant.cache.CacheKey;
import com.dolaing.core.common.constant.state.ManagerStatus;
import com.dolaing.core.common.constant.state.MenuStatus;
import com.dolaing.core.log.LogObjectHolder;
import com.dolaing.core.support.StrKit;
import com.dolaing.core.util.Convert;
import com.dolaing.core.util.SpringContextHolder;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.member.dao.PayAccountMapper;
import com.dolaing.modular.member.model.UserPayAccount;
import com.dolaing.modular.system.dao.MenuMapper;
import com.dolaing.modular.system.dao.NoticeMapper;
import com.dolaing.modular.system.dao.RoleMapper;
import com.dolaing.modular.system.dao.UserMapper;
import com.dolaing.modular.system.model.Menu;
import com.dolaing.modular.system.model.Notice;
import com.dolaing.modular.system.model.Role;
import com.dolaing.modular.system.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * 常量的生产工厂
 *
 * @author zx
 * @date 2018年2月13日 下午10:55:21
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {

    private RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);
    private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
    private MenuMapper menuMapper = SpringContextHolder.getBean(MenuMapper.class);
    private NoticeMapper noticeMapper = SpringContextHolder.getBean(NoticeMapper.class);
    private PayAccountMapper payAccountMapper = SpringContextHolder.getBean(PayAccountMapper.class);

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    /**
     * 根据用户id获取用户名称
     *
     * @author zx
     * @Date 2018/5/9 23:41
     */
    @Override
    public String getUserNameById(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            return user.getName();
        } else {
            return "--";
        }
    }

    /**
     * 根据用户id获取用户账号
     *
     * @author zx
     * @date 2018年5月16日21:55:371
     */
    @Override
    public String getUserAccountById(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            return user.getAccount();
        } else {
            return "--";
        }
    }

    /**
     * 通过角色ids获取角色名称
     */
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.ROLES_NAME + "'+#roleIds")
    public String getRoleName(String roleIds) {
        Integer[] roles = Convert.toIntArray(roleIds);
        StringBuilder sb = new StringBuilder();
        for (int role : roles) {
            Role roleObj = roleMapper.selectById(role);
            if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 通过角色id获取角色名称
     */
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleId")
    public String getSingleRoleName(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    /**
     * 通过角色id获取角色英文名称
     */
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_TIP + "'+#roleId")
    public String getSingleRoleTip(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getTips();
        }
        return "";
    }

    /**
     * 获取菜单名称
     */
    @Override
    public String getMenuName(Long menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            return "";
        } else {
            Menu menu = menuMapper.selectById(menuId);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取菜单名称通过编号
     */
    @Override
    public String getMenuNameByCode(String code) {
        if (ToolUtil.isEmpty(code)) {
            return "";
        } else {
            Menu param = new Menu();
            param.setCode(code);
            Menu menu = menuMapper.selectOne(param);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取用户登录状态
     */
    @Override
    public String getStatusName(Integer status) {
        return ManagerStatus.labelOf(status);
    }

    /**
     * 获取菜单状态
     */
    @Override
    public String getMenuStatusName(Integer status) {
        return MenuStatus.valueOf(status);
    }

    /**
     * 根据id获取银行卡尾号
     */
    @Override
    public String getBankCardNoById(Integer bankCardId) {
        UserPayAccount userPayAccount = payAccountMapper.selectById(bankCardId);
        if (userPayAccount != null) {
            String cardNo = userPayAccount.getCardNo();
            return cardNo.substring(cardNo.length() - 4, cardNo.length());
        } else {
            return "--";
        }
    }

    /**
     * 获取客户类型名称
     */
    @Override
    public String getCustTypeName(String custType) {
        if (custType.equals("0")) {
            return "个人";
        } else if (custType.equals("1")) {
            return "企业";
        } else {
            return "--";
        }
    }

    /**
     * 获取支付平台名称
     */
    @Override
    public String getPaymentName(String payment) {
        if (payment.equals("0")) {
            return "证联支付";
        } else {
            return "-";
        }
    }

    /**
     * 获取银行名称根据银行代码
     */
    @Override
    public String getBankNameByCode(String bankCode) {
        String bank = "--";
        switch (bankCode) {
            case "0102":
                bank = "中国工商银行";
                break;
            case "0103":
                bank = "中国农业银行";
                break;
            case "0104":
                bank = "中国银行";
                break;
            case "0105":
                bank = "中国建设银行";
                break;
            case "0301":
                bank = "交通银行";
                break;
            case "0302":
                bank = "中信银行";
                break;
            case "0303":
                bank = "中国光大银行";
                break;
            case "0304":
                bank = "华夏银行";
                break;
            case "0305":
                bank = "中国民生银行";
                break;
            case "0306":
                bank = "广东发展银行";
                break;
            case "0307":
                bank = "深圳发展银行";
                break;
            case "0308":
                bank = "招商银行";
                break;
            case "0309":
                bank = "兴业银行";
                break;
            case "0310":
                bank = "上海浦东发展银行";
                break;
        }
        return bank;
    }

    @Override
    public String getUserTypeName(String type) {
        if (null == type) {

        } else if (type.equals("0")) {
            return "管理员";
        } else if (type.equals("1")) {
            return "买家";
        } else if (type.equals("2")) {
            return "卖家";
        } else if (type.equals("3")) {
            return "农户";
        }
        return null;
    }
}
