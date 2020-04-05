layui.define(['common', 'form'], function (exports) {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.$;
    //监听提交
    form.on('submit(saveBtn)', function (data) {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "../update/",
            data: data.field,
            success: function(result){
                if(result.type === 'success'){
                    layer.msg("操作成功", {time: 1000},function(){
                        var iframeIndex = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(iframeIndex);
                        window.parent.location.href="/admin/storage_plugin/index/";
                    });
                }else{
                    layer.msg(result.content, {time: 2000});
                }
            }
        });
    });
    exports('storage_plugin/file_setting', {});
});