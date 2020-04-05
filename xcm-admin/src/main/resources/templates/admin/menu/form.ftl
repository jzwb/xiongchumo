<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Admin 后台管理系统 - 菜单管理 - 编辑</title>
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
        <input type="hidden" name="id" value="${menu.id}">
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">标题</label>
        <div class="layui-input-block">
            <input type="text" name="title" lay-verify="required" lay-reqtext="名称不能为空" placeholder="请输入标题" value="${menu.title}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">上级分类</label>
        <div class="layui-input-block">
            <select name="parentId" lay-filter="aihao">
                <option value=""></option>
                [#list menus as item]
                    [#if item != menu && (!children?has_content || !children?seq_contains(item))]
                        <option value="${item.id}" [#if item == menu.parent]selected=""[/#if]>[#if item.grade !=0][#list 1..item.grade as i]&nbsp;&nbsp;[/#list][/#if]${item.title}</option>
                    [/#if]
                [/#list]
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">链接</label>
        <div class="layui-input-block">
            <input type="text" name="href" value="${menu.href}" placeholder="请输入链接" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">打开方式</label>
        <div class="layui-input-block">
            <select name="target" lay-filter="aihao">
                <option value="_self" [#if menu.target == '_self'][/#if]>_self</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">权限</label>
        <div class="layui-input-block">
            <input type="text" name="authority" value="${menu.authority}" placeholder="请输入权限" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">图标</label>
        <div class="layui-input-block">
            <input type="text" name="icon" value="${menu.icon}" placeholder="请输入图标" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">排序</label>
        <div class="layui-input-block">
            <input type="text" name="order" value="${menu.order}" placeholder="请输入排序" class="layui-input">
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
    }).use('menu/form');
</script>
</body>
</html>