layui.define(['common', 'layer', 'echarts'], function (exports) {
    var $ = layui.$,
        echarts = layui.echarts;
    //日志分析
    var logAnalysis = function(){
        var echartsRecords = echarts.init(document.getElementById('echarts-records'), 'walden');
        $.ajax({
            method:"get",
            url:"/admin/index/log_analysis/",
            success:function (result) {
                if(result.type === 'success'){
                    var date = result.data.date;
                    var pv = result.data.pv;
                    var uv = result.data.uv;
                    _render(date,pv,uv);
                }
            }
        });
        var _render = function(date, pv, uv){
            var optionRecords = {
                title: {
                    text: '系统访问日志'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        label: {
                            backgroundColor: '#6a7985'
                        }
                    }
                },
                legend: {
                    data: ['PV', 'UV']
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        boundaryGap: false,
                        data: date
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: 'PV',
                        type: 'line',
                        stack: '总量',
                        areaStyle: {},
                        data: pv
                    },
                    {
                        name: 'UV',
                        type: 'line',
                        areaStyle: {},
                        data: uv
                    }
                ]
            };
            echartsRecords.setOption(optionRecords);
        };
        return echartsRecords;
    }();
    //窗口缩放自适应
    window.onresize = function () {
        logAnalysis.resize();
    };
    exports('index', {});
});