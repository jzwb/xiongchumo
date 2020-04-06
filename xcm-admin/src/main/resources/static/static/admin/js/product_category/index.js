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
        elem: '#product-category-table',
        url: '/admin/product_category/list/',
        page: false,
        cols: [[
            {type: 'numbers'},
            {field: 'name', minWidth: 200, title: '名称'},
            {field: 'order', width: 80, align: 'center', title: '排序'},
            {templet: '#auth-state', width: 120, align: 'center', title: '操作'}
        ]],
        done: function () {
            layer.closeAll('loading');
        }
    });

    $('#btn-expand').click(function () {
        treetable.expandAll('#product-category-table');
    });

    $('#btn-fold').click(function () {
        treetable.foldAll('#product-category-table');
    });

    //添加
    $(".data-add-btn").on("click", function () {
        var index = layer.open({
            title: '添加商品分类',
            type: 2,
            shade: 0.2,
            maxmin:true,
            shadeClose: true,
            area: ['100%', '100%'],
            content: '/admin/product_category/form/',
        });
        $(window).on("resize", function () {
            layer.full(index);
        });
        return false;
    });

    //监听工具条
    table.on('tool(product-category-table)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'del') {
            del([data.id]);
        } else if (layEvent === 'edit') {
            var index = layer.open({
                title: '编辑商品分类',
                type: 2,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['100%', '100%'],
                content: '/admin/product_category/form/?id=' + data.id,
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
                url:"/admin/product_category/delete/",
                data:{
                    ids : ids.join(',')
                },
                success:function (result) {
                    if(result.type === 'success'){
                        layer.msg("操作成功", {time: 1000}, function () {
                            layer.close(index);
                            window.location.href = "/admin/product_category/index/";
                        });
                    }else{
                        layer.msg(result.content, {time: 2000});
                    }
                }
            })
        });
    }
    exports('product_category/index', {});
});