/**
 * 字典管理初始化
 */
var Data = {
    id: "DataTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Data.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '字典名称', field: 'dictName', align: 'center', valign: 'middle', sortable: true},
        {title: '字典标签', field: 'dictLabel', align: 'center', valign: 'middle', sortable: true},
        {title: '字典英文标签', field: 'dictEnLabel', align: 'center', valign: 'middle', sortable: true},
        {title: '字典值', field: 'dictValue', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'remarks', align: 'center', valign: 'middle', sortable: true}];
};

$(function () {
    var defaultColunms = Data.initColumn();
    var table = new BSTable(Data.id, "/data/list", defaultColunms);
    table.setPaginationType("client");
    Data.table = table.init();
});

/**
 * 检查是否选中
 */
Data.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Data.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加字典
 */
Data.openAddData = function () {
    var index = layer.open({
        type: 2,
        title: '添加字典',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/data/data_add'
    });
    this.layerIndex = index;
};


/**
 * 点击修改按钮时
 */
Data.openChangeData = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改字典',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/data/data_edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除字典
 */
Data.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/data/delete", function () {
                Feng.success("删除成功!");
                Data.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", Data.seItem.id);
            ajax.start();
        };

        Feng.confirm("确定删除字典吗 "+ "?",operation);
    }
};

/**
 * 搜索字典
 */
Data.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    Data.table.refresh({query: queryData});
}

