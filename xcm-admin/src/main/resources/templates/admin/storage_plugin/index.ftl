<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Admin 后台管理系统 - 存储插件管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/admin/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/admin/css/public.css" media="all">
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <script type="text/html" id="toolbarDemo">
            <div class="layui-btn-container"></div>
        </script>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
    </div>
</div>
<script src="/static/admin/plugins/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript">
    layui.config({
        base: '/static/admin/js/'
    }).use('storage_plugin/index');
</script>
</body>
</html>