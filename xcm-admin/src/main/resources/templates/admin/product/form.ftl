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
        <input type="hidden" name="id" value="${product.id}">
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">标题</label>
        <div class="layui-input-block">
            <input type="text" name="title" lay-verify="required" lay-reqtext="标题不能为空" placeholder="请输入标题" value="${product.title}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">子标题</label>
        <div class="layui-input-block">
            <input type="text" name="subTitle" placeholder="请输入子标题" value="${product.subTitle}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">商品分类</label>
        <div class="layui-input-block">
            <select name="productCategoryId">
                <option value=""></option>
                [#list productCategories as item]
                    <option value="${item.id}" [#if item == product.productCategory]selected=""[/#if]>[#if item.grade !=0][#list 1..item.grade as i]&nbsp;&nbsp;[/#list][/#if]${item.name}</option>
                [/#list]
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">生产商</label>
        <div class="layui-input-block">
            <select name="producerId">
                <option value=""></option>
                [#list producers as item]
                    <option value="${item.id}" [#if item == product.producer]selected=""[/#if]>${item.name}</option>
                [/#list]
            </select>
        </div>
    </div>
    <div class="layui-form-item files-upload">
        <label class="layui-form-label">图片</label>
        <div class="layui-input-block">
            <button type="button" class="layui-btn files-upload-btn" lay-data="{data:{fileType:'image'}}" data-input-name="images">
                <i class="layui-icon">&#xe67c;</i>上传图片
            </button>
            <table class="layui-table">
                <colgroup>
                    <col width="50%"><col>
                    <col width="150"><col>
                </colgroup>
                <thead>
                <tr>
                    <th>图片</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    [#list product.images as image]
                        <tr>
                            <td>
                                <input type="text" name="images" value="${image}" class="layui-input"/>
                            </td>
                            <td>
                                <button type="button" class="layui-btn layui-btn-sm layui-btn-danger del-btn"><i class="layui-icon">&#xe640;</i></button>
                            </td>
                        </tr>
                    [/#list]
                </tbody>
            </table>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">浏览量</label>
        <div class="layui-input-block">
            <input type="text" name="views" value="${product.views}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">点赞数</label>
        <div class="layui-input-block">
            <input type="text" name="likes" value="${product.likes}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">内容</label>
        <div class="layui-input-block">
            <input type="text" name="content" value="${product.content}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">设置</label>
        <div class="layui-input-block">
            <input type="checkbox" name="isTop" lay-skin="primary" title="是否置顶" [#if product.isTop]checked=""[/#if]>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">排序</label>
        <div class="layui-input-block">
            <input type="text" name="order" value="${product.order}" class="layui-input">
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
    }).use(['product/form']);
</script>
</body>
</html>