/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var RolInfoDlg = {
    dataInfoData: {},
    validateFields: {
        dictName: {
            validators: {
                notEmpty: {
                    message: '字典名称不能为空'
                }
            }
        },
        dictLabel: {
            validators: {
                notEmpty: {
                    message: '字典标签不能为空'
                }
            }
        },
        dictEnLabel: {
            validators: {
                notEmpty: {
                    message: '字典英文标签不能为空'
                }
            }
        },
        dictValue: {
            validators: {
                notEmpty: {
                    message: '字典值为空或重复，请重新输入'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
RolInfoDlg.clearData = function () {
    this.roleInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RolInfoDlg.set = function (key, value) {
    this.dataInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RolInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
RolInfoDlg.close = function () {
    parent.layer.close(window.parent.Data.layerIndex);
};

/**
 * 收集数据
 */
RolInfoDlg.collectData = function () {
    this.set('id').set('dictName').set('dictLabel').set('dictEnLabel').set('dictValue').set('remarks');
};

/**
 * 验证数据是否为空
 */
RolInfoDlg.validate = function () {
    $('#dataInfoForm').data("bootstrapValidator").resetForm();
    $('#dataInfoForm').bootstrapValidator('validate');
    return $("#dataInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
RolInfoDlg.addSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/data/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Data.table.refresh();
        RolInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败" + data.responseJSON.message + "!");
    });
    ajax.set(this.dataInfoData);
    ajax.start();
};

/**
 * 提交修改字典
 */
RolInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    /*//判断是否重复
    var ajax1 = new $ax(Feng.ctxPath + "/data/repeat", function (data) {
        if(data.code == 300){
            $("#dictValue").val("");
            return ;
        }
    });
    ajax1.set(this.dataInfoData);
    ajax1.start();*/

    if (!this.validate()) {
        return;
    }


    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/data/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Data.table.refresh();
        RolInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败" + data.responseJSON.message + "!");
    });
    ajax.set(this.dataInfoData);
    ajax.start();
};

$(function () {
    Feng.initValidator("dataInfoForm", RolInfoDlg.validateFields);
});