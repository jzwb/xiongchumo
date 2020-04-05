layui.define(['common', 'form'], function (exports) {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.$;
    //监听提交
    form.verify({
        rePassword: function(value, item){
            if($('input[name="password"]').val() !== value){
                return '两次输入密码不一致';
            }
        },
        role: function(value, item){
            var name = $(item).attr('name');
            if(!$('input[name="'+name+'"]').is(':checked')){
                return '请选择角色';
            }
        }
    });
    form.on('submit(saveBtn)', function (data) {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/admin/admin/save/",
            data: data.field,
            success: function(result){
                if(result.type === 'success'){
                    layer.msg("操作成功", {time: 1000},function(){
                        var iframeIndex = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(iframeIndex);
                        window.parent.location.href="/admin/admin/index/";
                    });
                }else{
                    layer.msg(result.content, {time: 2000});
                }
            }
        });
    });
    exports('admin/form', {});
});