package com.dolaing.modular.mall.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.modular.mall.model.OrderInfo;
import com.dolaing.modular.mall.dao.OrderInfoMapper;
import com.dolaing.modular.mall.service.IOrderInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dolaing.modular.mall.vo.OrderInfoVo;
import com.dolaing.modular.member.dao.PayAccountMapper;
import com.dolaing.modular.system.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zx
 * @since 2018-08-04
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {
    @Resource
    private OrderInfoMapper orderInfoMapper;

    /**
     * 根据用户查询订单-分页查询
     * @param user
     * @return
     */
    public Page queryOrdersByUser(Page page ,User user){
        Map map = new HashMap();
        map.put("page",page);
        map.put("user",user);
        Integer count = orderInfoMapper.queryOrdersCountByUser(user) ;
        if(count <=0){
            page.setRecords(Collections.emptyList());
        }
        List<OrderInfoVo> orderInfoVos = orderInfoMapper.queryOrdersByUser(map);
        page.setTotal(count);
        page.setRecords(orderInfoVos);
        return page ;
    }

}
