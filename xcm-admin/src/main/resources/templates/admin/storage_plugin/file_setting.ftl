<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Admin 后台管理系统 - 存储插件管理 - 本地存储配置</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/admin/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/admin/css/public.css" media="all">
    <style>
        body {
            background-color: #ffffff;
        }
        .authority{
            background-color: #f6f6f6;
        }
    </style>
</head>
<body>

<div class="layui-form layuimini-form">
    <div class="layui-form-item">
        <label class="layui-form-label required">排序</label>
        <div class="layui-input-block">
            <input type="text" name="order" placeholder="请输入排序" value="${pluginConfig.order}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="saveBtn">确认保存</button>
        </div>
    </div>
</div>
<script src="/static/admin/plugins/layui/layui.js" charset="utf-8"></script>
<script>
    layui.config({
        base: '/static/admin/js/'
    }).use('storage_plugin/file_setting');
</script>
</body>
</html>