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
    </style>
</head>
<body>

<div class="layui-form layuimini-form">
    <div class="layui-form-item">
        <input type="hidden" name="id" value="${productCategory.id}">
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" lay-verify="required" lay-reqtext="名称不能为空" placeholder="请输入标题" value="${productCategory.name}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">上级分类</label>
        <div class="layui-input-block">
            <select name="parentId" lay-filter="aihao">
                <option value=""></option>
                [#list productCategories as item]
                    [#if item != productCategory && (!children?has_content || !children?seq_contains(item))]
                        <option value="${item.id}" [#if item == productCategory.parent]selected=""[/#if]>[#if item.grade !=0][#list 1..item.grade as i]&nbsp;&nbsp;[/#list][/#if]${item.name}</option>
                    [/#if]
                [/#list]
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">排序</label>
        <div class="layui-input-block">
            <input type="text" name="order" value="${productCategory.order}" placeholder="请输入排序" class="layui-input">
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
    }).use('product_category/form');
</script>
</body>
</html>