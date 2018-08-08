package com.dolaing.modular.system.controller;

import com.dolaing.StartupRunner;
import com.dolaing.core.base.controller.BaseController;
import com.dolaing.core.base.tips.Tip;
import com.dolaing.core.common.annotion.BussinessLog;
import com.dolaing.core.common.annotion.Permission;
import com.dolaing.core.common.constant.Const;
import com.dolaing.core.common.constant.dictmap.DictionaryDict;
import com.dolaing.core.common.exception.BizExceptionEnum;
import com.dolaing.core.exception.DolaingException;
import com.dolaing.core.log.LogObjectHolder;
import com.dolaing.core.shiro.ShiroKit;
import com.dolaing.core.util.ToolUtil;
import com.dolaing.modular.system.model.Dictionary;
import com.dolaing.modular.system.service.IDictionaryService;
import com.dolaing.modular.system.warpper.DictionaryDataWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author zx
 * @create 2018-07-02 9:38
 * @desc 数据字典业务处理
 **/
@Controller
@RequestMapping("/data")
public class DictionaryController extends BaseController {

    private String PREFIX = "/system/data/";

    private Dictionary temp = null;

    @Autowired
    private IDictionaryService IDictionaryService;
    @Autowired
    private StartupRunner startupRunner;

    /**
     * 跳转到数据字典管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "data.html";
    }

    /**
     * 跳转到添加字典
     */
    @RequestMapping("/data_add")
    public String dataAdd() {
        return PREFIX + "data_add.html";
    }

    /**
     * 跳转到修改字典
     */
    @Permission
    @RequestMapping(value = "/data_edit/{id}")
    public String dataEdit(@PathVariable Integer id, Model model) {
        if (ToolUtil.isEmpty(id)) {
            throw new DolaingException(BizExceptionEnum.REQUEST_NULL);
        }
        Dictionary dictionary = this.IDictionaryService.selectById(id);

        temp = IDictionaryService.selectById(id);

        model.addAttribute("data", dictionary);
        model.addAttribute(temp);
        LogObjectHolder.me().set(dictionary);
        return PREFIX + "/data_edit.html";
    }

    /**
     * 获取所有字典列表
     */
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String name) {
        if (ShiroKit.isAdmin()) {
            List<Map<String, Object>> dictionarys = IDictionaryService.selectDictionarys(null, name);
            return new DictionaryDataWarpper(dictionarys).warp();
        }
        return null;
    }

    /**
     * 新增字典
     */
    @BussinessLog(value = "增加字典名称", key = "dictName", dict = DictionaryDict.class)
    @RequestMapping(value = "/add")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip add(@Valid Dictionary dictionary, BindingResult result) {
        if (result.hasErrors()) {
            throw new DolaingException(BizExceptionEnum.REQUEST_NULL);
        }

        //判断字典是否重复
        Dictionary param = new Dictionary();
        param.setDictName(dictionary.getDictName());
        param.setDictValue(dictionary.getDictValue());
        if (IDictionaryService.selectOne(param) != null) {
            throw new DolaingException(BizExceptionEnum.DICT_EXISTED);
        }
        dictionary.setId(null);
        this.IDictionaryService.insert(dictionary);
        startupRunner.flushDictionary();
        return SUCCESS_TIP;
    }


    /**
     * 字典修改
     */
    @BussinessLog(value = "修改字典名称", key = "dictName", dict = DictionaryDict.class)
    @RequestMapping(value = "/update")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip update(@Valid Dictionary dictionary, BindingResult result) {
        if (result.hasErrors()) {
            throw new DolaingException(BizExceptionEnum.REQUEST_NULL);
        }

        //判断字典是否重复
        if (!dictionary.getDictValue().equals(temp.getDictValue())) {
            Dictionary param = new Dictionary();
            param.setDictName(dictionary.getDictName());
            param.setDictValue(dictionary.getDictValue());
            if (IDictionaryService.selectOne(param) != null) {
                throw new DolaingException(BizExceptionEnum.DICT_EXISTED);
            }
        }
        this.IDictionaryService.updateById(dictionary);
        startupRunner.flushDictionary();
        return SUCCESS_TIP;
    }

    /**
     * 删除字典
     */
    @BussinessLog(value = "删除字典名称", key = "id", dict = DictionaryDict.class)
    @RequestMapping(value = "/delete")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip delete(@RequestParam Integer id) {
        if (ToolUtil.isEmpty(id)) {
            throw new DolaingException(BizExceptionEnum.REQUEST_NULL);
        }
        this.IDictionaryService.deleteById(id);
        startupRunner.flushDictionary();
        return SUCCESS_TIP;
    }

}