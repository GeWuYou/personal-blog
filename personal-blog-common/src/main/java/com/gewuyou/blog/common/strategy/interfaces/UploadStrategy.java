package com.gewuyou.blog.common.strategy.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 上传策略
 *
 * @author gewuyou
 * @since 2024-05-05 下午9:30:06
 */
public interface UploadStrategy {
    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @param path 上传路径
     * @return 上传后的路径
     */
    String uploadFile(MultipartFile file, String path);

    /**
     * 上传文件
     *
     * @param fileName    上传的文件名
     * @param inputStream 上传的文件流
     * @param path        上传路径
     * @return 上传后的路径
     */

    String uploadFile(String fileName, InputStream inputStream, String path);
}
