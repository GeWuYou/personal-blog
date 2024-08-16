package com.gewuyou.blog.admin.strategy.impl;


import com.gewuyou.blog.admin.config.entity.MinioProperties;
import com.gewuyou.blog.common.service.IRedisService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

/**
 * Minio 上传策略实现
 *
 * @author gewuyou
 * @since 2024-05-06 下午6:34:01
 */
@Service("minioUploadStrategyImpl")
@Slf4j
public class MinioUploadStrategyImpl extends AbstractUploadStrategyImpl {

    private final MinioProperties minioProperties;

    @Lazy
    private volatile MinioClient minioClient;

    @Autowired
    public MinioUploadStrategyImpl(MinioProperties minioProperties, IRedisService redisService, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutoExecutor) {
        super(redisService, asyncTaskExecutoExecutor);
        this.minioProperties = minioProperties;
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return true：存在；false：不存在
     */
    @Override
    public Boolean exists(String filePath) {
        try {
            getMinioClient().statObject(StatObjectArgs
                    .builder()
                    .bucket(minioProperties.getBucketName())
                    .object(filePath)
                    .build());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 上传文件
     *
     * @param path        上传路径
     * @param fileName    上传文件名
     * @param inputStream 上传文件流
     * @throws IOException IO异常
     */
    @Override
    public void upload(String path, String fileName, InputStream inputStream) throws Exception {
        getMinioClient()
                .putObject(PutObjectArgs
                        .builder()
                        .bucket(minioProperties.getBucketName())
                        .object(path + fileName)
                        .stream(inputStream, inputStream.available(), -1)
                        .build()
                );
    }

    /**
     * 获取文件访问地址
     *
     * @param filePath 文件路径
     * @return 文件访问地址
     */
    @Override
    public String getFileAccessUrl(String filePath) {
        return minioProperties.getUrl() + "/" + filePath;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    @Override
    public void delete(String filePath) {
        try {
            getMinioClient().removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(minioProperties.getBucketName())
                            .object(filePath)
                            .build()
            );
        } catch (Exception e) {
            log.error("删除文件失败：{}", e.getMessage());
        }
    }

    private MinioClient getMinioClient() {
        if (minioClient == null) {
            synchronized (this) {
                if (minioClient == null) {
                    minioClient = MinioClient.builder()
                            .endpoint(minioProperties.getEndpoint())
                            .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                            .build();
                }
            }
        }
        return minioClient;
    }
}
