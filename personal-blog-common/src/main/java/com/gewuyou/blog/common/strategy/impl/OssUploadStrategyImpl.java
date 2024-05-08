package com.gewuyou.blog.common.strategy.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.gewuyou.blog.admin.config.entity.OssConfigProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Oss上传策略实现
 *
 * @author gewuyou
 * @since 2024-05-06 下午7:34:34
 */
@Service("ossUploadStrategyImpl")
public class OssUploadStrategyImpl extends AbstractUploadStrategyImpl {

    private final OssConfigProperties ossConfigProperties;

    private OSS ossClient;

    @Autowired
    public OssUploadStrategyImpl(OssConfigProperties ossConfigProperties) {
        this.ossConfigProperties = ossConfigProperties;
    }

    @PostConstruct
    public void init() {
        ossClient =
                new OSSClientBuilder()
                        .build(
                                ossConfigProperties.getEndpoint(),
                                ossConfigProperties.getAccessKeyId(),
                                ossConfigProperties.getSecretAccessKey());
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return true：存在；false：不存在
     */
    @Override
    public Boolean exists(String filePath) {
        return ossClient.doesObjectExist(ossConfigProperties.getBucketName(), filePath);
    }

    /**
     * 上传文件
     *
     * @param path        上传路径
     * @param fileName    上传文件名
     * @param inputStream 上传文件流
     */
    @Override
    public void upload(String path, String fileName, InputStream inputStream) {
        ossClient.putObject(ossConfigProperties.getBucketName(), path + fileName, inputStream);
    }

    /**
     * 获取文件访问地址
     *
     * @param filePath 文件路径
     * @return 文件访问地址
     */
    @Override
    public String getFileAccessUrl(String filePath) {
        return ossConfigProperties.getUrl() + filePath;
    }
}
