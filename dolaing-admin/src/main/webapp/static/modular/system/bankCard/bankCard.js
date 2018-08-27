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
        {field: 'selectItem', radio: true},
        {title: '用户账号', field: 'userId', align: 'center', valign: 'middle'},
        {title: '支付平台', field: 'payment', align: 'center', valign: 'middle'},
        {title: '用户姓名', field: 'userNameText', align: 'center', valign: 'middle', sortable: true},
        {title: '客户类型', field: 'custType', align: 'center', valign: 'middle', sortable: true},
        {title: '银行代码', field: 'bankCode', align: 'center', valign: 'middle', sortable: true},
        {title: '银行卡号', field: 'cardNoLastFour', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createtime', align: 'center', valign: 'middle', sortable: true}
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
BankCard.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/bankCard/delete", function (data) {
                Feng.success("删除成功!");
                BankCard.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("bankCardId", BankCard.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否解绑银行卡 " + BankCard.seItem.title + "?", operation);
    }
};

/**
 * 查询解绑银行卡列表
 */
BankCard.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BankCard.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = BankCard.initColumn();
    var table = new BSTable(BankCard.id, "/bankCard/list", defaultColunms);
    table.setPaginationType("client");
    BankCard.table = table.init();
});
