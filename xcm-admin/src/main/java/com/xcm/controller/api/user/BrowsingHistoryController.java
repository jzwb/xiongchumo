package com.xcm.controller.api.user;

import com.xcm.common.Message;
import com.xcm.model.User;
import com.xcm.service.BrowsingHistoryService;
import com.xcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * controller - 浏览历史
 */
@Controller("apiUserBrowsingHistoryController")
@RequestMapping("/api/user/browsing_history")
public class BrowsingHistoryController {

    @Autowired
    private UserService userService;
    @Autowired
    private BrowsingHistoryService browsingHistoryService;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Message list(@RequestParam(defaultValue = "1") Integer pageNumber) {
        User user = userService.getCurrent();
        List<Map<String, Object>> list = browsingHistoryService.findList(pageNumber, 10, user);
        return Message.success("请求成功", list);
    }

    /**
     * 清空
     *
     * @return
     */
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    @ResponseBody
    public Message clear() {
        User user = userService.getCurrent();
        browsingHistoryService.deleteByUser(user);
        return Message.success("请求成功");
    }
}