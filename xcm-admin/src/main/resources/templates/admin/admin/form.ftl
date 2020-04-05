<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Admin 后台管理系统 - 用户管理 - 编辑</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/admin/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/admin/css/public.css" media="all">
    <style>
        body {
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<div class="layui-form layuimini-form">
    <div class="layui-form-item">
        <input type="hidden" name="id" value="${admin.id}">
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">用户名</label>
        <div class="layui-input-block">
            <input type="text" name="username" lay-verify="required" lay-reqtext="用户名不能为空" placeholder="请输入用户名" value="${admin.username}" class="layui-input">
        </div>
    </div>
    [#if !admin?has_content]
    <div class="layui-form-item">
        <label class="layui-form-label required">密码</label>
        <div class="layui-input-block">
            <input type="password" name="password" lay-verify="required" lay-reqtext="密码不能为空" placeholder="请输入密码" value="" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">确认密码</label>
        <div class="layui-input-block">
            <input type="password" name="rePassword" lay-verify="required|rePassword" lay-reqtext="确认密码不能为空" placeholder="请输入确认密码" value="" class="layui-input">
        </div>
    </div>
    [#else]
    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-block">
            <input type="password" name="password" placeholder="请输入密码" value="" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">确认密码</label>
        <div class="layui-input-block">
            <input type="password" name="rePassword" lay-verify="rePassword" placeholder="请输入确认密码" value="" class="layui-input">
        </div>
    </div>
    [/#if]
    <div class="layui-form-item">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-block">
            <input type="text" name="name" value="${admin.name}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机</label>
        <div class="layui-input-block">
            <input type="text" name="mobile" value="${admin.mobile}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-block">
            <input type="text" name="email" value="${admin.email}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">角色</label>
        <div class="layui-input-block">
        [#list roles as role]
            <input type="checkbox" name="roleIds" value="${role.id}" lay-skin="primary" title="${role.name}" lay-verify="role" [#if admin?? && admin.roles?seq_contains(role)]checked=""[/#if]>
        [/#list]
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
    }).use(['admin/form']);
</script>
</body>
</html>