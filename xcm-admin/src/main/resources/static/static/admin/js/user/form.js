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
        checkEmailUnique: function(value, item){
            var errorMsg;
            $.ajax({
                type: "GET",
                dataType: "json",
                async:false,
                url: "/admin/user/check_email_unique/",
                data: {
                    oldEmail : $(item).attr("value"),
                    newEmail : value
                },
                success: function(result){
                    if(result.type === 'success'){
                        if(!result.data){
                            errorMsg = "邮箱已存在";
                        }
                    }else{
                        errorMsg = result.content;
                    }
                },
                error: function(){
                    errorMsg = "邮箱校验失败";
                }
            });
            return errorMsg;
        },
        checkMobileUnique: function(value){
            var errorMsg;
            $.ajax({
                type: "GET",
                dataType: "json",
                async:false,
                url: "/admin/user/check_mobile_unique/",
                data: {
                    mobile : value
                },
                success: function(result){
                    if(result.type === 'success'){
                        if(!result.data){
                            errorMsg = "手机已存在";
                        }
                    }else{
                        errorMsg = result.content;
                    }
                },
                error: function(){
                    errorMsg = "手机校验失败";
                }
            });
            return errorMsg;
        }
    });
    form.on('submit(saveBtn)', function (data) {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/admin/user/save/",
            data: data.field,
            success: function(result){
                if(result.type === 'success'){
                    layer.msg("操作成功", {time: 1000},function(){
                        var iframeIndex = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(iframeIndex);
                        window.parent.location.href="/admin/user/index/";
                    });
                }else{
                    layer.msg(result.content, {time: 2000});
                }
            }
        });
    });
    exports('user/form', {});
});