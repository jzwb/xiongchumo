layui.define(['common', 'form', 'table'], function (exports) {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;
    //列表
    table.render({
        elem: '#currentTableId',
        url: '/admin/storage_plugin/list/',
        parseData: function(res){
            return {
                "code": res.type === 'success' ? '0' : '-1',
                "msg": res.content,
                "data": res.data
            };
        },
        toolbar: '#toolbarDemo',
        defaultToolbar: [],
        cols: [[
            {type: "checkbox", width: 50, fixed: "left"},
            {field: 'name', align:'center', title: '名称',unresize:true},
            {field: 'version', align:'center', title: '描述',unresize:true},
            {title: '操作', minWidth: 50, templet: function(d){
                var currentTableBarInstall = '<a class="layui-btn layui-btn-xs data-count-install" lay-event="install">安装</a>';
                var currentTableBarUninstall = '<a class="layui-btn layui-btn-xs layui-btn-danger data-count-uninstall" lay-event="uninstall">卸载</a>';
                var currentTableBarSetting = '<a class="layui-btn layui-btn-xs data-count-setting" lay-event="setting">设置</a>';
                var newHtml = '';
                if(d.isInstalled){
                    if(d.settingUrl !== ''){
                        newHtml += currentTableBarSetting;
                    }
                    if(d.uninstallUrl !== ''){
                        newHtml += currentTableBarUninstall;
                    }
                }else{
                    if(d.installUrl !== ''){
                        newHtml += currentTableBarInstall;
                    }
                }
                return newHtml;
            }, fixed: "right", align: "center"}
        ]],
        page: false
    });

    table.on('tool(currentTableFilter)', function (obj) {
        var data = obj.data;
        if(obj.event === 'setting'){
            var index = layer.open({
                title: '编辑角色',
                type: 2,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['100%', '100%'],
                content: data.settingUrl,
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
        }else if(obj.event === 'install'){
            install(data);
        }else if(obj.event === 'uninstall'){
            uninstall(data);
        }
    });
    //安装
    function install(data){
        if(!data){
            return layer.msg("参数错误", {time: 2000});
        }
        $.ajax({
            method:"POST",
            url: data.installUrl,
            success:function (result) {
                if(result.type === 'success'){
                    layer.msg("操作成功", {time: 1000}, function () {
                        window.location.href = "/admin/storage_plugin/index/";
                    });
                }else{
                    layer.msg(result.content, {time: 2000});
                }
            }
        })
    }
    //卸载
    function uninstall(data) {
        if(!data){
            return layer.msg("参数错误", {time: 2000});
        }
        $.ajax({
            method:"POST",
            url: data.uninstallUrl,
            success:function (result) {
                if(result.type === 'success'){
                    layer.msg("操作成功", {time: 1000}, function () {
                        window.location.href = "/admin/storage_plugin/index/";
                    });
                }else{
                    layer.msg(result.content, {time: 2000});
                }
            }
        });
    }
    exports('role/index', {});
});
