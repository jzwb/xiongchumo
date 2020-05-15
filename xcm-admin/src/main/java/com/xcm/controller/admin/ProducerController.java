package com.xcm.controller.admin;

import com.xcm.common.Message;
import com.xcm.common.Page;
import com.xcm.common.Pageable;
import com.xcm.model.Producer;
import com.xcm.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * controller - 生产商管理
 */
@Controller("adminProducerController")
@RequestMapping("/admin/producer")
public class ProducerController extends BaseController {

    @Autowired
    private ProducerService producerService;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        return "admin/producer/index";
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
        Page<Producer> page = producerService.findPage(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("content", page.getContent());
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
        modelMap.addAttribute("producer", producerService.find(id));
        modelMap.addAttribute("types", Producer.Type.values());
        return "/admin/producer/form";
    }

    /**
     * 保存
     *
     * @param producer
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Message save(Producer producer) {
        Producer pProducer = producerService.find(producer.getId());
        if (pProducer == null) {
            pProducer = new Producer();
        }
        pProducer.setName(producer.getName());
        pProducer.setType(producer.getType());
        pProducer.setImage(producer.getImage());
        pProducer.setViews(producer.getViews());
        pProducer.setLikes(producer.getLikes());
        pProducer.setContent(producer.getContent());
        if (pProducer.getId() == null) {
            producerService.save(pProducer);
        } else {
            producerService.update(pProducer);
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
        producerService.delete(ids);
        return Message.success();
    }
}