package com.xcm.service;

import com.xcm.common.FileInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service - 文件上传
 */
public interface FileService {

    /**
     * 文件验证
     *
     * @param fileType      文件类型
     * @param multipartFile 上传文件
     * @return
     */
    boolean isValid(FileInfo.FileType fileType, MultipartFile multipartFile);

    /**
     * 文件上传
     *
     * @param fileType      文件类型
     * @param multipartFile 上传文件
     * @return
     */
    String upload(FileInfo.FileType fileType, MultipartFile multipartFile);
}
