package com.dolaing.modular.api;

import com.dolaing.config.properties.DolaingProperties;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.base.tips.SuccessTip;
import com.dolaing.core.common.constant.Const;
import com.dolaing.core.common.constant.JwtConstants;
import com.dolaing.core.util.JwtTokenUtil;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.api.base.BaseApi;
import com.dolaing.modular.mall.model.MallGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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

    /**
     * 发布商品
     */
    @PostMapping("/publishGoods")
    public Object publish(@RequestBody MallGoods mallGoods) {
        String requestHeader = getHeader(JwtConstants.AUTH_HEADER);
        String userName = "";
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            userName = JwtTokenUtil.getUsernameFromToken(requestHeader.substring(7));
        }
        if (ToolUtil.isOneEmpty(mallGoods.getGoodsName(), mallGoods.getShopPrice())) {
            return new ErrorTip(500, "产品发布失败，参数有空值");
        }
        mallGoods.setCreateBy(userName);
        mallGoods.setCreateTime(new Date());
        mallGoods.insert();
        return SUCCESS_TIP;
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
