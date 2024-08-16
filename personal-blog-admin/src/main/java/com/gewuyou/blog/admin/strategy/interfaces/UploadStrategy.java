package com.gewuyou.blog.admin.strategy.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

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
    CompletableFuture<String> uploadFile(MultipartFile file, String path);

    /**
     * 上传文件
     *
     * @param fileName    上传的文件名
     * @param inputStream 上传的文件流
     * @param path        上传路径
     * @return 上传后的路径
     */

    CompletableFuture<String> uploadFile(String fileName, InputStream inputStream, String path);

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    void delete(String filePath);
}
