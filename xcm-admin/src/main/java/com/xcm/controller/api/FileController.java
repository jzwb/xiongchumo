package com.xcm.controller.api;

import com.xcm.common.FileInfo;
import com.xcm.common.Message;
import com.xcm.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * controller - 上传
 */
@Controller("apiFileController")
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param fileType
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Message upload(FileInfo.FileType fileType, @RequestParam CommonsMultipartFile file) {
        if (!fileService.isValid(fileType, file)) {
            return Message.error("文件验证失败");
        }
        String filePath = fileService.upload(fileType, file);
        if (StringUtils.isBlank(filePath)) {
            return Message.error("上传失败");
        }
        return Message.success("上传成功", filePath);
    }
}
