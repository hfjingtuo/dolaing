package com.dolaing.modular.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.config.properties.DolaingProperties;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.base.tips.SuccessTip;
import com.dolaing.core.common.annotion.AuthAccess;
import com.dolaing.core.common.constant.Const;
import com.dolaing.core.support.HttpKit;
import com.dolaing.core.util.DateUtil;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.api.base.Result;
import com.dolaing.modular.mall.model.MallGoods;
import com.dolaing.modular.mall.model.MallShop;
import com.dolaing.modular.mall.service.MallGoodsService;
import com.dolaing.modular.mall.service.MallShopService;
import com.dolaing.modular.mall.vo.MallGoodsVo;
import com.dolaing.modular.system.model.User;
import com.dolaing.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: zx
 * Date: Created in 2018/07/25 11:44
 * Copyright: Copyright (c) 2018
 * Description： 商品控制器
 */
@RestController
@RequestMapping("/dolaing")
public class GoodsApi extends BaseApi {

    @Autowired
    private DolaingProperties dolaingPropertie;
    @Autowired
    private MallGoodsService mallGoodsService;
    @Autowired
    private MallShopService mallShopService;
    @Autowired
    private IUserService userService;

    /**
     * 商品详情
     */
    @GetMapping("/goods/detail/{goodsId}")
    public Object detail(@PathVariable Integer goodsId) {
        HashMap<String, Object> result = new HashMap<>();
        MallGoods mallGoods = new MallGoods().selectById(goodsId);
        if (mallGoods != null) {
            MallShop mallShop = new MallShop().selectById(mallGoods.getShopId());
            result.put("mallGoods", mallGoods);
            result.put("mallShop", mallShop);
            return result;
        }
        return new ErrorTip(500, "商品不存在");
    }

    /**
     * 发布商品
     */
    @AuthAccess
    @PostMapping("/publishGoods")
    public Object publish(@RequestBody MallGoods mallGoods) {
        String token = JwtTokenUtil.getToken(HttpKit.getRequest());
        String account = JwtTokenUtil.getAccountFromToken(token);
        if (ToolUtil.isOneEmpty(mallGoods.getGoodsName(), mallGoods.getShopPrice())) {
            return new ErrorTip(500, "产品发布失败，参数有空值");
        }
        Wrapper<MallShop> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", account);
        MallShop mallShop = mallShopService.selectOne(wrapper);
        mallGoods.setBrandId(mallShop.getBrandId());
        mallGoods.setBrandName(mallGoods.getBrandName());
        mallGoods.setCreateBy(account);
        mallGoods.setCreateTime(new Date());
        mallGoods.insert();
        return SUCCESS_TIP;
    }

    /**
     * 已发布商品列表
     */
    @AuthAccess
    @GetMapping("/publishGoods/list")
    public Result index(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        String token = JwtTokenUtil.getToken(HttpKit.getRequest());
        String account = JwtTokenUtil.getAccountFromToken(token);
        Page<MallGoodsVo> page = new Page(pageNo, pageSize);
        page = mallGoodsService.getGoodsList(page, account);
        return render(page);
    }

    /**
     * 修改商品
     */
    @AuthAccess
    @PostMapping("/updateGoods")
    public Object update(@RequestBody MallGoods mallGoods) {
        MallGoods old = new MallGoods().selectById(mallGoods.getId());
        if (ToolUtil.isOneEmpty(mallGoods.getGoodsName(), mallGoods.getShopPrice())) {
            return new ErrorTip(500, "产品发布失败，参数有空值");
        }
        old.setGoodsName(mallGoods.getGoodsName());
        old.setBreeds(mallGoods.getBreeds());
        old.setCatId(mallGoods.getCatId());
        old.setDepositRatio(mallGoods.getDepositRatio());
        old.setExpectPartOutput(mallGoods.getExpectPartOutput());
        old.setShopPrice(mallGoods.getShopPrice());
        old.setStartSubscribeTime(mallGoods.getStartSubscribeTime());
        old.setEndSubscribeTime(mallGoods.getEndSubscribeTime());
        old.setPlantime(mallGoods.getPlantime());
        old.setPlantingCycle(mallGoods.getPlantingCycle());
        old.setGoodsMasterImgs(mallGoods.getGoodsMasterImgs());
        old.setLandAddress(mallGoods.getLandAddress());
        old.setLandPartArea(mallGoods.getLandPartArea());
        old.setLandSn(mallGoods.getLandSn());
        old.setLangImgs(mallGoods.getLangImgs());
        old.setGoodsDesc(mallGoods.getGoodsDesc());
        old.setGoodsDescImgs(mallGoods.getGoodsDescImgs());
        //预计发货时间=认购结束时间+生长周期
        old.setExpectDeliverTime(DateUtil.plusDay(mallGoods.getPlantingCycle(), mallGoods.getEndSubscribeTime()));
        old.updateById();
        return SUCCESS_TIP;
    }

    /**
     * 删除商品
     */
    @AuthAccess
    @PostMapping("/deleteGoods")
    public Object delete(@RequestParam Integer goodsId) {
        if (ToolUtil.isEmpty(goodsId)) {
            return new ErrorTip(500, "商品不存在");
        }
        new MallGoods().deleteById(goodsId);
        return new SuccessTip(200, "删除成功");
    }

    /**
     * 根据卖家查找所属农户
     */
    @AuthAccess
    @PostMapping("/getAllFarmer")
    public Object getAllFarmer() {
        String token = JwtTokenUtil.getToken(HttpKit.getRequest());
        String account = JwtTokenUtil.getAccountFromToken(token);
        List<User> list = userService.getFarmerByParentAccount(account);
        if (list != null) {
            return render(list);
        }
        return new ErrorTip(500, "没找到所属农户");
    }

    /**
     * 上传商品图片
     */
    @RequestMapping("/upload/goodsImage")
    public Object uploadImage(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            Calendar date = Calendar.getInstance();
            String name = new SimpleDateFormat("yyyyMM").format(date.getTime());
            String filePath = dolaingPropertie.getFileUploadPath() + Const.GOODS_IMG + name + "/";
            File filePathDir = new File(filePath);
            if (!(filePathDir.exists() && filePathDir.isDirectory())) {
                filePathDir.mkdirs();
            }
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + new Random().nextInt(99) + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            filePath = filePath + fileName;
            System.out.println("filePath=" + filePath);
            if (saveFile(file, filePath)) {
                return new SuccessTip(200, fileName);
            }
        }
        return new ErrorTip(500, "上传图片异常");
    }

    /**
     * 保存文件
     *
     * @param file
     * @param path
     * @return
     */
    private boolean saveFile(MultipartFile file, String path) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 转存文件
                file.transferTo(new File(path));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
