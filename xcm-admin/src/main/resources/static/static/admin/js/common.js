layui.define(["jquery","upload"], function(exports){
    var topWin = (function(){ var p=window.parent; while(p!=p.window.parent){p=p.window.parent;} return p; })();
    layui.$.ajaxSetup({
        complete:function(xhr,status){
            ajaxComplete(xhr);
        }
    });
    layui.jquery.ajaxSetup({
        complete:function(xhr,status){
            ajaxComplete(xhr);
        }
    });
    var ajaxComplete = function(xhr){
        var loginStatus = xhr.getResponseHeader("loginStatus");
        if(loginStatus === 'accessDenied'){
            layer.msg("登录超时", {time: 2000}, function(){
                topWin.location.reload();
            });
        }
    };

    var $ = layui.$,
        upload = layui.upload;

    //文件上传
    var fileUpload = function(){
        //执行实例
        var instance = upload.render({
            elem: '.file-upload-btn',
            url: '/admin/file/upload/',
            done: function(result){
                var item = this.item;
                if(result.type === 'success'){
                    var inputName = $(item).attr('data-input-name');
                    $(item).closest("div").find('input[name="' + inputName + '"]').val(result.data);
                }else{
                    layer.msg(result.content, {time: 2000});
                }
            },
            error: function(){
                layer.msg("请求异常", {time: 2000});
            }
        });
        return instance;
    }();

    var filesUpload = function(){
        //执行实例
        var instance = upload.render({
            elem: '.files-upload-btn',
            url: '/admin/file/upload/',
            done: function(result){
                var item = this.item;
                if(result.type === 'success'){
                    var inputName = $(item).attr('data-input-name');
                    var trHtml = '<tr><td><input type="text" name="' + inputName + '" value="' + result.data + '" class="layui-input"></td><td><button type="button" class="layui-btn layui-btn-sm layui-btn-danger del-btn"><i class="layui-icon">&#xe640;</i></button></td></tr>';
                    $(item).closest("div").find('table tbody').append(trHtml);
                }else{
                    layer.msg(result.content, {time: 2000});
                }
            },
            error: function(){
                layer.msg("请求异常", {time: 2000});
            }
        });
        //删除
        $(document).on('click', '.files-upload .del-btn', function(){
            $(this).closest("tr").remove();
        });
        return instance;
    }();
    exports('common', {
        topWin : topWin
    });
});