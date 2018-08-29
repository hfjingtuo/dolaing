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
                var html = '<a title="解除绑定" style="color: #1ab394;cursor:pointer" onclick="BankCard.delete(\'' + row.id + '\',\'' + row.userId + '\',\'' + row.cardNoLastFour + '\')">解除绑定</a>';
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
BankCard.delete = function (id, account, cardNoLastFour) {
    var operation = function () {
        var loadIndex = layer.load(0, {shade: [0.1, '#f5f5f5']}); //0代表加载的风格，支持0-2;
        $.ajax({
            url: Feng.ctxPath + "/bankCard/delete",
            type: "POST",
            data: "account=" + account + "&cardNoLastFour=" + cardNoLastFour + "&bankCardId=" + id,
            success: function (data) {
                if (data.code == 200) {
                    Feng.success("解除绑定成功");
                    BankCard.noSearch();
                } else if (data.code == 500) {
                    Feng.error(data.message);
                }
                layer.close(loadIndex);
            },
            error: function (data) {
                Feng.error("解除绑定失败!" + data.responseJSON.message + "!");
                layer.close(loadIndex);
            }
        });
    };
    Feng.confirm("是否解绑" + account + "的尾号为 " + cardNoLastFour + " 的银行卡(仅针对无法正常使用的开户情况，有效的开户必须谨慎操作)?", operation);

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
