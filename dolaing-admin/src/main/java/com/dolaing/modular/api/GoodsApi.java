package com.dolaing.modular.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dolaing.config.properties.DolaingProperties;
import com.dolaing.core.base.tips.ErrorTip;
import com.dolaing.core.base.tips.SuccessTip;
import com.dolaing.core.common.annotion.AuthAccess;
import com.dolaing.core.common.constant.Const;
import com.dolaing.core.common.constant.GlobalData;
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
import com.dolaing.modular.system.model.Dictionary;
import com.dolaing.modular.system.model.User;
import com.dolaing.modular.system.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
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

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

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
    @PostMapping("/goods/detail")
    public Object detail(@RequestParam String goodsId) {
        HashMap<String, Object> result = new HashMap<>();
        MallGoods mallGoods = new MallGoods().selectById(goodsId);
        if (mallGoods != null) {
            MallShop mallShop = new MallShop().selectById(mallGoods.getShopId());
            result.put("mallGoods", mallGoods);
            result.put("mallShop", mallShop);
            String catId = mallGoods.getCatId() + "";
            String catName = "";
            List<Dictionary> list = GlobalData.DICTIONARY_ARR.get("catId");
            for (Dictionary dictionary : list) {
                if (dictionary.getDictValue().equals(catId)) {
                    catName = dictionary.getDictLabel();
                }
            }
            Integer province = mallShop.getProvince();
            Integer city = mallShop.getCity();
            result.put("address", GlobalData.AREAS.get(province).getChName() + GlobalData.AREAS.get(city).getChName());
            result.put("catName", catName);
            return render(result);
        }
        return new ErrorTip(500, "商品不存在");
    }

    /**
     * 发布商品
     */
    @AuthAccess
    @PostMapping("/publishGoods")
    public Object publish(HttpServletRequest request) {
        try {
            String token = JwtTokenUtil.getToken(HttpKit.getRequest());
            String account = JwtTokenUtil.getAccountFromToken(token);

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> masterImgs = multipartRequest.getFiles("masterImgs");
            List<MultipartFile> landImgs = multipartRequest.getFiles("landImgs");
            List<MultipartFile> descImgs = multipartRequest.getFiles("descImgs");

            String goodsId = getPara("goodsId");
            String goodsName = getPara("goodsName");
            String shopPrice = getPara("shopPrice");
            String depositRatio = getPara("depositRatio");
            String isFreeShipping = getPara("isFreeShipping");
            String catId = getPara("catId");
            String breeds = getPara("breeds");
            String plantime = getPara("plantime");
            String plantingCycle = getPara("plantingCycle");
            String expectPartOutput = getPara("expectPartOutput");
            String landSn = getPara("landSn");
            String landAddress = getPara("landAddress");
            String landPartArea = getPara("landPartArea");
            String goodsNumber = getPara("goodsNumber");
            String goodsDesc = getPara("goodsDesc");
            String farmerId = getPara("farmerId");
            String startSubscribeTime = getPara("startSubscribeTime");
            String endSubscribeTime = getPara("endSubscribeTime");

            String masterImgsUrl = getPara("masterImgsUrl");
            String landImgsUrl = getPara("landImgsUrl");
            String descImgsUrl = getPara("descImgsUrl");

            if (ToolUtil.isOneEmpty(goodsName, shopPrice, depositRatio, isFreeShipping, catId, breeds, plantime, plantingCycle, expectPartOutput, landSn, landAddress, landPartArea, goodsNumber, goodsDesc, farmerId, startSubscribeTime, endSubscribeTime)) {
                return new ErrorTip(500, "商品发布异常，请重新发布");
            }

            MallGoods mallGoods;
            if (StringUtils.isNotBlank(goodsId)) {
                mallGoods = mallGoodsService.selectById(goodsId);
                if (!account.equals(mallGoods.getCreateBy())){
                    return new ErrorTip(500, "商品发布异常，您无权限编辑此商品");
                }
            } else {
                mallGoods = new MallGoods();
            }
            Wrapper<MallShop> wrapper = new EntityWrapper<>();
            wrapper.eq("user_id", account);
            MallShop mallShop = mallShopService.selectOne(wrapper);
            mallGoods.setGoodsName(goodsName);
            mallGoods.setShopPrice(new BigDecimal(shopPrice));
            mallGoods.setDepositRatio(new BigDecimal(depositRatio).divide(BigDecimal.valueOf(100)));
            mallGoods.setIsFreeShipping(Integer.valueOf(isFreeShipping));
            mallGoods.setCatId(Integer.valueOf(catId));
            mallGoods.setBreeds(breeds);
            mallGoods.setPlantime(plantime);
            mallGoods.setPlantingCycle(Integer.valueOf(plantingCycle));
            mallGoods.setExpectPartOutput(new BigDecimal(expectPartOutput));
            mallGoods.setLandSn(landSn);
            mallGoods.setLandAddress(landAddress);
            mallGoods.setLandPartArea(new BigDecimal(landPartArea));
            mallGoods.setGoodsNumber(Integer.valueOf(goodsNumber));
            mallGoods.setGoodsDesc(goodsDesc);
            mallGoods.setFarmerId(farmerId);
            mallGoods.setStartSubscribeTime(format.parse(startSubscribeTime));
            mallGoods.setEndSubscribeTime(format.parse(endSubscribeTime));
            mallGoods.setGoodsSn("SN" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + new Random().nextInt(99));
            mallGoods.setShopId(mallShop.getId());
            mallGoods.setBrandId(mallShop.getBrandId());
            mallGoods.setBrandName(mallShop.getBrandName());
            //预计发货时间
            mallGoods.setExpectDeliverTime(DateUtil.plusDay(mallGoods.getPlantingCycle(), mallGoods.getEndSubscribeTime()));
            mallGoods.setCreateBy(account);
            mallGoods.setCreateTime(new Date());

            String masterImgsPath = saveGoodsImg(masterImgs);
            String landImgsPath = saveGoodsImg(landImgs);
            String descImgsPath = saveGoodsImg(descImgs);

            if (StringUtils.isNotBlank(goodsId)) {//编辑商品
                if (StringUtils.isNotBlank(masterImgsPath) && StringUtils.isNotBlank(masterImgsUrl)) {
                    mallGoods.setGoodsMasterImgs(masterImgsUrl + "," + masterImgsPath);
                } else if (StringUtils.isNotBlank(masterImgsUrl) && StringUtils.isBlank(masterImgsPath)) {
                    mallGoods.setGoodsMasterImgs(masterImgsUrl);
                } else if (StringUtils.isNotBlank(masterImgsPath) && StringUtils.isBlank(masterImgsUrl)) {
                    mallGoods.setGoodsMasterImgs(masterImgsPath);
                } else {
                    return new ErrorTip(500, "商品主图没有上传，请上传");
                }

                if (StringUtils.isNotBlank(landImgsPath) && StringUtils.isNotBlank(landImgsUrl)) {
                    mallGoods.setLandImgs(landImgsUrl + "," + landImgsPath);
                } else if (StringUtils.isNotBlank(landImgsUrl) && StringUtils.isBlank(landImgsPath)) {
                    mallGoods.setLandImgs(landImgsUrl);
                } else if (StringUtils.isNotBlank(landImgsPath) && StringUtils.isBlank(landImgsUrl)) {
                    mallGoods.setLandImgs(landImgsPath);
                } else {
                    return new ErrorTip(500, "土地图没有上传，请上传");
                }

                if (StringUtils.isNotBlank(descImgsPath) && StringUtils.isNotBlank(descImgsUrl)) {
                    mallGoods.setGoodsDescImgs(descImgsUrl + "," + descImgsPath);
                } else if (StringUtils.isNotBlank(descImgsUrl) && StringUtils.isBlank(descImgsPath)) {
                    mallGoods.setGoodsDescImgs(descImgsUrl);
                } else if (StringUtils.isNotBlank(descImgsPath) && StringUtils.isBlank(descImgsUrl)) {
                    mallGoods.setGoodsDescImgs(descImgsPath);
                } else {
                    return new ErrorTip(500, "商品详情图没有上传，请上传");
                }
                mallGoods.updateById();
            } else {//新增商品
                if ("".equals(masterImgsPath)) {
                    return new ErrorTip(500, "商品主图没有上传，请上传");
                } else {
                    mallGoods.setGoodsMasterImgs(masterImgsPath);
                }
                if ("".equals(landImgsPath)) {
                    return new ErrorTip(500, "土地图没有上传，请上传");
                } else {
                    mallGoods.setLandImgs(landImgsPath);
                }
                if ("".equals(descImgsPath)) {
                    return new ErrorTip(500, "商品详情图没有上传，请上传");
                } else {
                    mallGoods.setGoodsDescImgs(descImgsPath);
                }
                mallGoods.insert();
            }
            return SUCCESS_TIP;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return new ErrorTip(500, "系统异常");
    }

    /**
     * 已发布商品列表
     */
    @AuthAccess
    @PostMapping("/publishGoods/list")
    public Result index(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        String token = JwtTokenUtil.getToken(HttpKit.getRequest());
        String account = JwtTokenUtil.getAccountFromToken(token);
        Page<MallGoodsVo> page = new Page(pageNo, pageSize);
        page = mallGoodsService.getGoodsList(page, account);
        return render(page);
    }

    /**
     * 已发布商品详情：编辑商品
     */
    @AuthAccess
    @PostMapping("/publishedGoods/detail")
    public Object publishedDetail(@RequestParam String goodsId) {
        String token = JwtTokenUtil.getToken(HttpKit.getRequest());
        String account = JwtTokenUtil.getAccountFromToken(token);
        Wrapper<MallGoods> wrapper = new EntityWrapper<>();
        wrapper.eq("id", goodsId);
        wrapper.eq("create_by", account);
        wrapper.eq("del_flag", 0);
        MallGoods mallGoods = new MallGoods().selectById(goodsId);
        if (mallGoods != null) {
            return render(mallGoods);
        }
        return new ErrorTip(500, "商品不存在");
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
        old.setLandImgs(mallGoods.getLandImgs());
        old.setGoodsDesc(mallGoods.getGoodsDesc());
        old.setGoodsDescImgs(mallGoods.getGoodsDescImgs());
        //预计发货时间=认购结束时间+生长周期
        old.setExpectDeliverTime(DateUtil.plusDay(mallGoods.getPlantingCycle(), mallGoods.getEndSubscribeTime()));
        old.updateById();
        return SUCCESS_TIP;
    }

    @ApiOperation(value = "批量删除")
    @AuthAccess
    @PostMapping("/batchDeleteGoods")
    public Result batchDelete(@RequestParam String ids) {
        System.out.println("ids==" + ids);
        String token = JwtTokenUtil.getToken(HttpKit.getRequest());
        String account = JwtTokenUtil.getAccountFromToken(token);
        mallGoodsService.batchDelete(account, ids);
        return render(true);
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
     * 保存商品图片
     *
     * @param imgs
     * @return
     */
    private String saveGoodsImg(List<MultipartFile> imgs) {
        String savePath = "";
        if (imgs != null && imgs.size() > 0) {
            MultipartFile file;
            for (int i = 0; i < imgs.size(); ++i) {
                file = imgs.get(i);
                Calendar date = Calendar.getInstance();
                String name = new SimpleDateFormat("yyyyMM").format(date.getTime());
                String imgPath = Const.GOODS_IMG + name + "/";
                String filePath = dolaingPropertie.getFileUploadPath() + imgPath;
                File filePathDir = new File(filePath);
                if (!(filePathDir.exists() && filePathDir.isDirectory())) {
                    filePathDir.mkdirs();
                }
                String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + new Random().nextInt(99) + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                filePath = filePath + fileName;
                savePath += imgPath + fileName + ",";
                System.out.println("filePath=" + filePath);
                System.out.println("savePath=" + savePath);
                saveFile(file, filePath);
            }
            savePath = savePath.substring(0, savePath.length() - 1);
        }
        System.out.println("savePath:" + savePath);
        return savePath;
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
