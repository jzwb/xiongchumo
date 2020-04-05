package com.xcm.controller.admin;

import com.xcm.common.Message;
import com.xcm.service.LogService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * controller - 首页
 */
@Controller("adminIndexController")
@RequestMapping("/admin/index")
public class IndexController extends BaseController {

    @Autowired
    private LogService logService;

    /**
     * 日志分析
     *
     * @return
     */
    @GetMapping("/log_analysis")
    @ResponseBody
    public Message log_analysis() {
        Date endDate = new Date();
        Date startDate = DateUtils.addDays(endDate, -7);//近7天
        Map<String, Object> data = logService.analysis(startDate, endDate);
        return Message.success("成功", data);
    }
}