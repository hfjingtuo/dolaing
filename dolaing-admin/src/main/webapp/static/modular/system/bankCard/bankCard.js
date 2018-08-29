/**
 * 银行卡管理初始化
 */
var BankCard = {
    id: "BankCardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BankCard.initColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '用户账号', field: 'userId', align: 'center', valign: 'middle'},
        {title: '支付平台', field: 'paymentName', align: 'center', valign: 'middle'},
        {title: '用户姓名', field: 'userNameText', align: 'center', valign: 'middle'},
        {title: '手机号', field: 'mobile', align: 'center', valign: 'middle'},
        {title: '客户类型', field: 'custTypeName', align: 'center', valign: 'middle'},
        {title: '银行名称', field: 'bankCodeName', align: 'center', valign: 'middle'},
        {title: '银行卡尾号', field: 'cardNoLastFour', align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle'},
        {
            title: '操作', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var html = '<a title="解除绑定" style="color: #1ab394;cursor:pointer" onclick="BankCard.delete(\'' + row.id + '\',\'' + row.cardNoLastFour + '\')">解除绑定</a>';
                return html;
            }
        }
    ];
};

/**
 * 检查是否选中
 */
BankCard.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        BankCard.seItem = selected[0];
        return true;
    }
};

/**
 * 解绑银行卡
 */
BankCard.delete = function (id, cardNoLastFour) {

    var operation = function () {
        var ajax = new $ax(Feng.ctxPath + "/bankCard/delete", function (data) {
            if (data.code == 200){
                Feng.success("解除绑定成功!");
                BankCard.noSearch();
            } else if (data.code == 500) {
                Feng.error(data.message);
            }
        }, function (data) {
            Feng.error("解除绑定失败!" + data.responseJSON.message + "!");
        });
        ajax.set("bankCardId", id);
        ajax.start();
    };

    Feng.confirm("是否解绑尾号为 " + cardNoLastFour + " 的银行卡?", operation);

};

/**
 * 查询解绑银行卡列表
 */
BankCard.search = function () {
    $("#BankCardTable").parent().parent().show();
    if ($.trim($("#condition").val()) == null || $.trim($("#condition").val()) == "") {
        BankCard.noSearch();
    } else {
        BankCard.initInfo();
        var queryData = {};
        queryData['condition'] = $.trim($("#condition").val());
        BankCard.table.refresh({query: queryData});
    }
};

/**
 * 重置
 */
BankCard.resetSearch = function () {
    $("#condition").val("");
    BankCard.noSearch();
};

BankCard.noSearch = function () {
    var queryData = {};
    queryData['condition'] = "*";
    BankCard.table.refresh({query: queryData});
    $("#BankCardTable").parent().parent().hide();
};

BankCard.initInfo = function () {
    var defaultColunms = BankCard.initColumn();
    var table = new BSTable(BankCard.id, "/bankCard/list", defaultColunms);
    table.setPaginationType("client");
    table.setShowRefresh(false);
    table.setShowColumns(false);
    BankCard.table = table.init();
};

$(function () {

    // BankCard.noSearch();
});
