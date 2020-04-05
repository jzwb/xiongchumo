layui.define(['common', 'jquery', 'layer', 'miniAdmin', 'miniMenu'], function (exports) {
    var $ = layui.$,
        layer = layui.layer,
        miniAdmin = layui.miniAdmin,
        miniMenu = layui.miniMenu;

    var options = {
        // 初始化接口
        iniUrl: "/static/admin/json/init.json",
        // 缓存清理接口
        clearUrl: "/static/admin/json/clear.json",
        // 是否打开hash定位
        urlHashLocation: false,
        // 主题默认配置
        bgColorDefault: 0,
        // 是否开启多模块
        multiModule: false,
        // 是否默认展开菜单
        menuChildOpen: false,
        // 初始化加载时间
        loadingTime: 0,
        // iframe窗口动画
        pageAnim: true,
        // 最大的tab打开数量
        maxTabNum: 20
    };
    miniAdmin.render(options);

    //菜单初始化
    var menuInit = function(){
        $.ajax({
            method:"get",
            url:"/admin/common/menus/",
            success:function (result) {
                if(result.type === 'success'){
                    miniMenu.render({
                        menuList: list2tree(result.data),
                        multiModule: options.multiModule,
                        menuChildOpen: options.menuChildOpen
                    });
                }
            }
        })
    }();
    //list转树
    var list2tree = function(list){
        var newList = [];
        $.each(list,function(i,item){
            var map = {};
            map['id'] = item.id;
            map['title'] = item.title;
            map['href'] = item.href;
            map['icon'] = item.icon;
            map['target'] = item.target;
            map['parent'] = item.parent;
            newList.push(map);
        });
        $.each(newList,function(i,item){
            var parent = item.parent;
            if(parent !== -1){
                $.each(newList,function(j,jtem){
                    if(jtem.id === parent){
                        if(!jtem.child){
                            jtem.child = [];
                        }
                        jtem.child.push(item);
                    }
                })
            }
        });
        newList = newList.filter(function(item){
            return item.parent === -1;
        });
        return newList;
    };

    //退出登录
    $('.login-out').on("click", function () {
        layer.msg('退出登录成功');
        setTimeout(function(){
            window.location = '/admin/logout/';
        },500)
    });

    exports('main', {});
});