package com.gewuyou.blog.admin.strategy.impl;

import com.gewuyou.blog.admin.config.entity.LocalProperties;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.service.IRedisService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.Executor;

/**
 * 本地上传策略实现
 *
 * @author gewuyou
 * @since 2024-07-16 下午11:23:08
 */
@Service("localUploadStrategyImpl")
@Slf4j
public class LocalUploadStrategyImpl extends AbstractUploadStrategyImpl {
    /**
     * 存储根路径
     */
    private final String root;
    /**
     * 访问地址
     */
    private final String url;

    @Autowired
    public LocalUploadStrategyImpl(LocalProperties localProperties, IRedisService redisService, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutoExecutor) {
        super(redisService, asyncTaskExecutoExecutor);
        this.root = localProperties.getRoot();
        this.url = localProperties.getUrl();
    }

    @PostConstruct
    public void init() {
        // 判断根路径是否存在，不存在则创建
        var dir = new File(root);
        // 存在且为目录，则不做任何操作
        if (dir.exists() && dir.isDirectory()) {
            return;
        }
        var success = dir.mkdirs();
        if (success) {
            log.info("创建根目录：{}", root);
        } else {
            log.error("创建根目录失败：{}", root);
            throw new GlobalException(ResponseInformation.CREATE_DIRECTORY_FAILED);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return true：存在；false：不存在
     */
    @Override
    public Boolean exists(String filePath) {
        // 检查文件是否存在
        return new File(root + filePath).exists();
    }

    /**
     * 上传文件
     *
     * @param path  上传路径
     * @param fileName    上传文件名
     * @param inputStream 上传文件流
     * @throws Exception 异常
     */
    @Override
    public void upload(String path, String fileName, InputStream inputStream) throws Exception {
        // 确保上传目录存在
        var directory = new File(root + path);
        if (!directory.exists()) {
            var success = directory.mkdirs();
            if (success) {
                log.info("创建本地上传目录：{}", directory.getAbsolutePath());
            }
        }
        // 写入文件
        var file = new File(directory, fileName);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * 获取文件访问地址
     *
     * @param filePath 文件路径
     * @return 文件访问地址
     */
    @Override
    public String getFileAccessUrl(String filePath) {
        return url + filePath;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    @Override
    public void delete(String filePath) {
        var file = new File(root + filePath);
        if (file.exists()) {
            var success = file.delete();
            if (success) {
                log.info("删除本地文件：{}", filePath);
            } else {
                log.error("删除本地文件失败：{}", filePath);
            }
        }
    }
}
