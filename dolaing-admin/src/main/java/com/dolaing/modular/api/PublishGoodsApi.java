package com.dolaing.modular.api;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.dolaing.config.properties.DolaingProperties;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.base.tips.SuccessTip;
import com.dolaing.core.common.constant.Const;
import com.dolaing.core.common.constant.JwtConstants;
import com.dolaing.core.util.DateUtil;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.mall.model.MallGoods;
import com.dolaing.modular.mall.model.MallShop;
import com.dolaing.modular.mall.service.MallGoodsService;
import com.dolaing.modular.mall.vo.MallGoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: zx
 * Date: Created in 2018/07/27 11:44
 * Copyright: Copyright (c) 2018
 * Description： 卖家发布商品控制器
 */
@RestController
@RequestMapping("/dolaing/seller")
public class PublishGoodsApi extends BaseApi {

    @Autowired
    private DolaingProperties dolaingPropertie;
    @Autowired
    private MallGoodsService mallGoodsService;

    /**
     * 发布商品
     */
    @PostMapping("/publishGoods")
    public Object publish(@RequestBody MallGoods mallGoods) {
        String requestHeader = getHeader(JwtConstants.AUTH_HEADER);
        String account = "";
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            account = JwtTokenUtil.getAccountFromToken(requestHeader.substring(7));
        }
        if (ToolUtil.isOneEmpty(mallGoods.getGoodsName(), mallGoods.getShopPrice())) {
            return new ErrorTip(500, "产品发布失败，参数有空值");
        }
        mallGoods.setCreateBy(account);
        mallGoods.setCreateTime(new Date());
        mallGoods.insert();
        return SUCCESS_TIP;
    }

    /**
     * 已发布商品列表
     */
    @GetMapping("/publishGoods/list")
    public Map index(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        String requestHeader = getHeader(JwtConstants.AUTH_HEADER);
        String userName = "";
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            userName = JwtTokenUtil.getAccountFromToken(requestHeader.substring(7));
        }
        Map<String, Object> map = new HashMap<>();
        Pagination page = new Pagination(pageNo, pageSize);
        List<MallGoodsVo> list = mallGoodsService.getGoodsList(page, StringUtils.isBlank(userName) ? "2" : userName);
        map.put("list", list);
        System.out.println(map);
        return map;
    }

    /**
     * 修改商品
     */
    @PostMapping("/updateGoods")
    public Object update(@RequestBody MallGoods mallGoods) {
        MallGoods old = new MallGoods().selectById(mallGoods.getId());
        if (ToolUtil.isOneEmpty(mallGoods.getGoodsName(), mallGoods.getShopPrice())) {
            return new ErrorTip(500, "产品发布失败，参数有空值");
        }
        old.setGoodsName(mallGoods.getGoodsName());
//        old.setBrandId(mallGoods.getBrandId());
        old.setBrandName(mallGoods.getBrandName());
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
    @PostMapping("/deleteGoods")
    public Object delete(@RequestParam Integer goodsId) {
        if (ToolUtil.isEmpty(goodsId)) {
            return new ErrorTip(500, "商品不存在");
        }
        new MallGoods().deleteById(goodsId);
        return new SuccessTip(200, "删除成功");
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
