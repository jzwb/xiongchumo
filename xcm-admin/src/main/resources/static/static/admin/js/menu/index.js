layui.define(['common', 'table', 'treetable'], function (exports) {
    var $ = layui.jquery;
    var table = layui.table;
    var treetable = layui.treetable;

    // 渲染表格
    layer.load(2);
    treetable.render({
        treeColIndex: 1,
        treeSpid: -1,
        treeIdName: 'id',
        treePidName: 'parent',
        elem: '#munu-table',
        url: '/admin/menu/list/',
        page: false,
        cols: [[
            {type: 'numbers'},
            {field: 'title', minWidth: 200, title: '权限名称'},
            {field: 'authority', title: '权限标识'},
            {field: 'href', title: '菜单url'},
            {field: 'order', width: 80, align: 'center', title: '排序'},
            {
                field: 'isMenu', width: 80, align: 'center', templet: function (d) {
                    if (d.isMenu == 1) {
                        return '<span class="layui-badge layui-bg-gray">按钮</span>';
                    }
                    if (d.parentId == -1) {
                        return '<span class="layui-badge layui-bg-blue">目录</span>';
                    } else {
                        return '<span class="layui-badge-rim">菜单</span>';
                    }
                }, title: '类型'
            },
            {templet: '#auth-state', width: 120, align: 'center', title: '操作'}
        ]],
        done: function () {
            layer.closeAll('loading');
        }
    });

    $('#btn-expand').click(function () {
        treetable.expandAll('#munu-table');
    });

    $('#btn-fold').click(function () {
        treetable.foldAll('#munu-table');
    });

    //添加
    $(".data-add-btn").on("click", function () {
        var index = layer.open({
            title: '添加角色',
            type: 2,
            shade: 0.2,
            maxmin:true,
            shadeClose: true,
            area: ['100%', '100%'],
            content: '/admin/menu/form/',
        });
        $(window).on("resize", function () {
            layer.full(index);
        });
        return false;
    });

    //监听工具条
    table.on('tool(munu-table)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'del') {
            del([data.id]);
        } else if (layEvent === 'edit') {
            var index = layer.open({
                title: '编辑菜单',
                type: 2,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['100%', '100%'],
                content: '/admin/menu/form/?id=' + data.id,
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
        }
    });

    //删除函数
    function del(ids) {
        if(!ids || ids.length === 0){
            return layer.msg("请选择删除数据", {time: 2000});
        }
        layer.confirm('确定删除?', function (index) {
            $.ajax({
                method:"POST",
                dataType: "json",
                url:"/admin/menu/delete/",
                data:{
                    ids : ids.join(',')
                },
                success:function (result) {
                    if(result.type === 'success'){
                        layer.msg("操作成功", {time: 1000}, function () {
                            layer.close(index);
                            window.location.href = "/admin/menu/index/";
                        });
                    }else{
                        layer.msg(result.content, {time: 2000});
                    }
                }
            })
        });
    }
    exports('menu/index', {});
});