layui.define(['common', 'form', 'table'], function (exports) {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;
    //列表
    table.render({
        elem: '#currentTableId',
        url: '/admin/admin/list/',
        request: {
            pageName: 'pageNumber'
            ,limitName: 'pageSize'
        },
        parseData: function(res){
            return {
                "code": res.type === 'success' ? '0' : '-1',
                "msg": res.content,
                "count": res.data.total,
                "data": res.data.content
            };
        },
        toolbar: '#toolbarDemo',
        defaultToolbar: [],
        cols: [[
            {type: "checkbox", width: 50, fixed: "left"},
            {field: 'username', align:'center', title: '用户名',unresize:true},
            {field: 'name', align:'center', title: '姓名',unresize:true},
            {field: 'email', align:'center', title: '邮箱',unresize:true},
            {field: 'createDate', align:'center', title: '创建日期',unresize:true},
            {title: '操作', minWidth: 50, templet: '#currentTableBar', fixed: "right", align: "center"},
        ]],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true
    });
    //添加
    $(".data-add-btn").on("click", function () {
        var index = layer.open({
            title: '添加用户',
            type: 2,
            shade: 0.2,
            maxmin:true,
            shadeClose: true,
            area: ['100%', '100%'],
            content: '/admin/admin/form/',
        });
        $(window).on("resize", function () {
            layer.full(index);
        });
        return false;
    });
    // 批量删除
    $(".data-delete-btn").on("click", function () {
        var checkStatus = table.checkStatus('currentTableId')
            , data = checkStatus.data;
        var ids = [];
        $.each(data,function(i,item){
            ids.push(item.id);
        });
        del(ids);
    });
    //编辑和删除
    table.on('tool(currentTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            var index = layer.open({
                title: '编辑用户',
                type: 2,
                shade: 0.2,
                maxmin:true,
                shadeClose: true,
                area: ['100%', '100%'],
                content: '/admin/admin/form/?id=' + data.id,
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
        } else if (obj.event === 'delete') {
            del([data.id]);
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
                url:"/admin/admin/delete/",
                data:{
                    ids : ids.join(',')
                },
                success:function (result) {
                    if(result.type === 'success'){
                        layer.msg("操作成功", {time: 1000}, function () {
                            layer.close(index);
                            window.location.href = "/admin/admin/index/";
                        });
                    }else{
                        layer.msg(result.content, {time: 2000});
                    }
                }
            })
        });
    }
    exports('admin/index', {});
});
