<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Admin 后台管理系统 - 角色管理 - 编辑</title>
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
        <input type="hidden" name="id" value="${role.id}">
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" lay-verify="required" lay-reqtext="名称不能为空" placeholder="请输入名称" value="${role.name}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">描述</label>
        <div class="layui-input-block">
            <input type="text" name="description" value="${role.description}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item authority">
        <label class="layui-form-label">系统管理</label>
        <div class="layui-input-block">
            <input type="checkbox" class="all-checked" lay-filter="all-checked" value="1" lay-skin="primary">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <input class="chekbox-group-1" type="checkbox" name="authorities" value="admin:admin" lay-skin="primary" title="用户管理" [#if role?has_content && role.authorities?seq_contains("admin:admin")] checked=""[/#if]>
            <input class="chekbox-group-1" type="checkbox" name="authorities" value="admin:role" lay-skin="primary" title="角色管理" [#if role?has_content && role.authorities?seq_contains("admin:role")] checked=""[/#if]>
            <input class="chekbox-group-1" type="checkbox" name="authorities" value="admin:menu" lay-skin="primary" title="菜单管理" [#if role?has_content && role.authorities?seq_contains("admin:menu")] checked=""[/#if]>
            <input class="chekbox-group-1" type="checkbox" name="authorities" value="admin:storage_plugin" lay-skin="primary" title="插件管理" [#if role?has_content && role.authorities?seq_contains("admin:storage_plugin")] checked=""[/#if]>
        </div>
    </div>
    [#if role.isSystem]
    <blockquote class="layui-elem-quote">
        内置角色不允许修改<br>
    </blockquote>
    [#else]
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="saveBtn">确认保存</button>
        </div>
    </div>
    [/#if]
</div>
<script src="/static/admin/plugins/layui/layui.js" charset="utf-8"></script>
<script>
    layui.config({
        base: '/static/admin/js/'
    }).use('role/form');
</script>
</body>
</html>