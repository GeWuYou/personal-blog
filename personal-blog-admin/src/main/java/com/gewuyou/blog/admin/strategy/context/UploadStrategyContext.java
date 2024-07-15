package com.gewuyou.blog.admin.strategy.context;

import com.gewuyou.blog.admin.strategy.interfaces.UploadStrategy;
import com.gewuyou.blog.common.enums.UploadModeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

/**
 * 上传策略上下文
 *
 * @author gewuyou
 * @since 2024-05-06 下午8:10:57
 */
@Service
public class UploadStrategyContext {
    @Value("${upload.mode}")
    private String uploadMode;

    private final Map<String, UploadStrategy> uploadStrategyMap;

    @Autowired
    public UploadStrategyContext(Map<String, UploadStrategy> uploadStrategyMap) {
        this.uploadStrategyMap = uploadStrategyMap;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 路径
     * @return 上传结果
     */
    public String executeUploadStrategy(MultipartFile file, String path) {
        return uploadStrategyMap.get(UploadModeEnum.getStrategy(uploadMode)).uploadFile(file, path);
    }

    /**
     * 上传文件
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @param path        路径
     * @return 上传结果
     */
    public String executeUploadStrategy(String fileName, InputStream inputStream, String path) {
        return uploadStrategyMap.get(UploadModeEnum.getStrategy(uploadMode)).uploadFile(fileName, inputStream, path);
    }
}
