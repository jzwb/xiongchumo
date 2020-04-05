<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Admin 后台管理系统 - 首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/admin/plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/admin/plugins/font-awesome/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="/static/admin/css/public.css" media="all">
</head>
<body>
<div class="layuimini-main">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-xs12 layui-col-md3">
            <div class="layui-card top-panel">
                <div class="layui-card-header">要展示的指标名称</div>
                <div class="layui-card-body">
                    <div class="layui-row layui-col-space5">
                        <div class="layui-col-xs9 layui-col-md9 top-panel-number">
                            0
                        </div>
                        <div class="layui-col-xs3 layui-col-md3 top-panel-tips">
                            比昨天 <a style="color: #1aa094">▲0</a><br>
                            比七日 <a style="color: #bd3004">▼0</a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div class="layui-col-xs12 layui-col-md3">
            <div class="layui-card top-panel">
                <div class="layui-card-header">要展示的指标名称</div>
                <div class="layui-card-body">
                    <div class="layui-row layui-col-space5">
                        <div class="layui-col-xs9 layui-col-md9 top-panel-number">
                            0
                        </div>
                        <div class="layui-col-xs3 layui-col-md3 top-panel-tips">
                            比昨天 <a style="color: #1aa094">▲0</a><br>
                            比七日 <a style="color: #bd3004">▼0</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-xs12 layui-col-md3">
            <div class="layui-card top-panel">
                <div class="layui-card-header">要展示的指标名称</div>
                <div class="layui-card-body">
                    <div class="layui-row layui-col-space5">
                        <div class="layui-col-xs9 layui-col-md9 top-panel-number">
                            0
                        </div>
                        <div class="layui-col-xs3 layui-col-md3 top-panel-tips">
                            比昨天 <a style="color: #1aa094">▲0</a><br>
                            比七日 <a style="color: #bd3004">▼0</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-xs12 layui-col-md3">
            <div class="layui-card top-panel">
                <div class="layui-card-header">要展示的指标名称</div>
                <div class="layui-card-body">
                    <div class="layui-row layui-col-space5">
                        <div class="layui-col-xs9 layui-col-md9 top-panel-number">
                            0
                        </div>
                        <div class="layui-col-xs3 layui-col-md3 top-panel-tips">
                            比昨天 <a style="color: #1aa094">▲0</a><br>
                            比七日 <a style="color: #bd3004">▼0</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-row layui-col-space15">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-xs12 layui-col-md12">
                <div id="echarts-records" style="background-color:#ffffff;min-height:400px;padding: 10px"></div>
            </div>
        </div>
    </div>
<script src="/static/admin/plugins/layui/layui.js" charset="utf-8"></script>
<script src="/static/admin/js/lay-config.js" charset="utf-8"></script>
<script>
    layui.config({
        base: '/static/admin/js/'
    }).use('index');
</script>
</body>
</html>
