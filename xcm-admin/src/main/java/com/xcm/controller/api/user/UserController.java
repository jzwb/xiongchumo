package com.xcm.controller.api.user;

import com.xcm.common.Message;
import com.xcm.model.User;
import com.xcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller - 用户
 */
@Controller("apiUserController")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 昵称修改
     *
     * @param nickName
     * @return
     */
    @RequestMapping(value = "/nick_name/update", method = RequestMethod.POST)
    @ResponseBody
    public Message nickNameUpdate(String nickName) {
        User user = userService.getCurrent();
        if (!userService.nickNameUnique(user.getNickName(), nickName)) {
            return Message.error("昵称已存在");
        }
        user.setNickName(nickName);
        userService.update(user);
        return Message.success("修改成功");
    }
}