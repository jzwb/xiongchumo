layui.define(['common', 'form'], function (exports) {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.$;
    //监听提交
    form.on('submit(saveBtn)', function (data) {
        var authorities = [];
        $("input:checkbox[name='authorities']:checked").each(function(){
            authorities.push($(this).val());
        });
        data.field.authorities = authorities.join(",");
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/admin/role/save/",
            data: data.field,
            success: function(result){
                if(result.type === 'success'){
                    layer.msg("操作成功", {time: 1000},function(){
                        var iframeIndex = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(iframeIndex);
                        window.parent.location.href="/admin/role/index/";
                    });
                }else{
                    layer.msg(result.content, {time: 2000});
                }
            }
        });
    });

    //全选处理
    $('.all-checked').each(function(i,item){
        var $item = $(item);
        var all = true;
        var $group = $(".chekbox-group-" + $item.val());
        $.each($group,function(j,jtem){
            var $jtem = $(jtem);
            if(!$jtem.prop('checked')){
                all = false;
                return false;
            }
        });
        if(all){
            $item.prop("checked", true);
            form.render('checkbox');
        }
    });
    form.on('checkbox(all-checked)', function(data){
        var group = ".chekbox-group-" + data.value;
        $(group).prop("checked", data.elem.checked);
        form.render('checkbox');
    });
    exports('role/form', {});
});