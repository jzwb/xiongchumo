<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin 后台管理系统 - 菜单管理</title>
    <link rel="stylesheet" href="/static/admin/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/admin/css/public.css" media="all">
    <style>
        .layui-btn:not(.layui-btn-lg ):not(.layui-btn-sm):not(.layui-btn-xs) {
            height: 34px;
            line-height: 34px;
            padding: 0 8px;
        }
    </style>
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <div>
            <div class="layui-btn-group">
                <button class="layui-btn" id="btn-expand">全部展开</button>
                <button class="layui-btn" id="btn-fold">全部折叠</button>
            </div>
            <div class="layui-btn-group">
                <button class="layui-btn layui-btn-sm data-add-btn"> 添加 </button>
            </div>
            <table id="munu-table" class="layui-table" lay-filter="munu-table"></table>
        </div>
    </div>
</div>
<!-- 操作列 -->
<script type="text/html" id="auth-state">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script src="/static/admin/plugins/layui/layui.js" charset="utf-8"></script>
<script src="/static/admin/js/lay-config.js" charset="utf-8"></script>
<script>
    layui.config({
        base: '/static/admin/js/'
    }).use('menu/index');
</script>
</body>
</html>