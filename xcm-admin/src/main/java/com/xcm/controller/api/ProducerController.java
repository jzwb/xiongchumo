package com.xcm.controller.api;

import com.xcm.common.Message;
import com.xcm.model.Producer;
import com.xcm.service.ProducerService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controller - 生厂商
 */
@Controller("apiProducerController")
@RequestMapping("/api/producer")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    /**
     * 列表
     *
     * @param pageNumber 页码
     * @param pageSize   页数量
     * @param type       类型
     * @param sortType   排序类型
     * @return
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public Message list(@RequestParam(defaultValue = "1", required = false) Integer pageNumber, @RequestParam(defaultValue = "100", required = false) Integer pageSize, Producer.Type type, @RequestParam(defaultValue = "NEW") Producer.SortType sortType) {
        List<Producer> producers = producerService.findList(pageNumber, pageSize, type, sortType);
        List<Map<String, Object>> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(producers)) {
            for (Producer producer : producers) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", producer.getId());
                map.put("name", producer.getName());
                map.put("image", producer.getImage());
                map.put("views", producer.getViews());
                map.put("likes", producer.getLikes());
                map.put("createDate", producer.getCreateDate().getTime());
                list.add(map);
            }
        }
        return Message.success("请求成功", list);
    }

    /**
     * 详情
     *
     * @param id 生厂商id
     * @return
     */
    @GetMapping(value = "/detail")
    @ResponseBody
    public Message detail(Long id) {
        Producer producer = producerService.find(id);
        if (producer == null) {
            return Message.error("生厂商不存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", producer.getId());
        map.put("name", producer.getName());
        map.put("type", producer.getType());
        map.put("image", producer.getImage());
        map.put("views", producer.getViews());
        map.put("likes", producer.getLikes());
        map.put("content", producer.getContent());
        map.put("createDate", producer.getCreateDate().getTime());
        return Message.success("请求成功", map);
    }

    /**
     * 点赞
     *
     * @param id 生厂商id
     * @return
     */
    @GetMapping(value = "/likes")
    @ResponseBody
    public Message likes(Long id) {
        Producer producer = producerService.find(id);
        if (producer == null) {
            return Message.error("生厂商不存在");
        }
        producer.setLikes(producer.getLikes() + 1);
        producerService.update(producer);
        return Message.success();
    }

    /**
     * 浏览
     *
     * @param id 生厂商id
     * @return
     */
    @GetMapping(value = "/views")
    @ResponseBody
    public Message views(Long id) {
        Producer producer = producerService.find(id);
        if (producer == null) {
            return Message.error("生厂商不存在");
        }
        producer.setViews(producer.getViews() + 1);
        producerService.update(producer);
        return Message.success();
    }
}