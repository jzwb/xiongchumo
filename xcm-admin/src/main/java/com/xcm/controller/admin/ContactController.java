package com.xcm.controller.admin;

import com.xcm.common.Message;
import com.xcm.common.Page;
import com.xcm.common.Pageable;
import com.xcm.model.Contact;
import com.xcm.service.ContactService;
import com.xcm.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controller - 联系我管理
 */
@Controller("adminContactController")
@RequestMapping("/admin/contact")
public class ContactController extends BaseController {

    @Autowired
    private ContactService contactService;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "admin/contact/index";
    }

    /**
     * 列表
     *
     * @param pageable
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Message list(Pageable pageable) {
        Page<Contact> page = contactService.findPage(pageable);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Contact contact : page.getContent()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", contact.getId());
            map.put("image", contact.getImage());
            map.put("description", contact.getDescription());
            map.put("createDate", DateTimeUtils.getFormatDate(contact.getCreateDate(), DateTimeUtils.FULL_DATE_FORMAT));
            mapList.add(map);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("content", mapList);
        return Message.success("成功", data);
    }

    /**
     * 表单
     *
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping("/form")
    public String form(Long id, ModelMap modelMap) {
        modelMap.addAttribute("contact", contactService.find(id));
        return "/admin/contact/form";
    }

    /**
     * 保存
     *
     * @param contact 联系我
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Message save(Contact contact) {
        Contact pContact = contactService.find(contact.getId());
        if (pContact == null) {
            pContact = new Contact();
        }
        pContact.setImage(contact.getImage());
        pContact.setDescription(contact.getDescription());
        if (pContact.getId() == null) {
            contactService.save(pContact);
        } else {
            contactService.update(pContact);
        }
        return Message.success();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Message delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return Message.error("参数错误");
        }
        contactService.delete(ids);
        return Message.success();
    }
}