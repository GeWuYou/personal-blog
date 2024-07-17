package com.gewuyou.blog.admin.strategy.impl;

import com.gewuyou.blog.admin.config.entity.QiNiuProperties;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * 七牛云上传策略实现
 *
 * @author gewuyou
 * @since 2024-05-18 下午8:19:42
 */
@Service("qiNiuUploadStrategyImpl")
@Slf4j
public class QiNiuUploadStrategyImpl extends AbstractUploadStrategyImpl {

    private final QiNiuProperties qiNiuProperties;

    @Lazy
    private volatile UploadManager uploadManager;
    @Lazy
    private volatile String uploadToken;
    @Lazy
    private volatile Configuration configuration;
    @Lazy
    private volatile BucketManager bucketManager;

    @Autowired
    public QiNiuUploadStrategyImpl(QiNiuProperties qiNiuProperties) {
        this.qiNiuProperties = qiNiuProperties;
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
            getBucketManager()
                    .stat(qiNiuProperties.getBucketName(), filePath);
            return true;
        } catch (QiniuException e) {
            return false;
        }
    }

    /**
     * 上传文件
     *
     * @param path        上传路径
     * @param fileName    上传文件名
     * @param inputStream 上传文件流
     * @throws Exception 异常
     */
    @Override
    public void upload(String path, String fileName, InputStream inputStream) throws Exception {
        try {
            var response = getUploadManager().put(inputStream, path + fileName, getUploadToken(), null, null);
            log.info("上传文件成功，返回信息：{}", response);
        } catch (QiniuException e) {
            log.error("上传文件失败，原因：{}", e.response.bodyString());
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
        log.info("获取文件访问地址：{}", filePath);
        return qiNiuProperties.getUrl() + "/" + filePath;
    }

    private UploadManager getUploadManager() {
        if (uploadManager == null) {
            uploadManager = new UploadManager(getConfiguration());
        }
        return uploadManager;
    }


    /**
     * 获取区域管理器
     *
     * @return 上传管理器
     */
    private BucketManager getBucketManager() {
        if (bucketManager == null) {
            bucketManager =
                    new BucketManager(Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey()), getConfiguration());
        }
        return bucketManager;
    }

    private String getUploadToken() {
        if (uploadToken == null) {
            uploadToken =
                    Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey()).uploadToken(qiNiuProperties.getBucketName());
        }
        return uploadToken;
    }

    private Configuration getConfiguration() {
        if (configuration == null) {
            Region region = switch (qiNiuProperties.getEndpoint()) {
                case "华东" -> Region.huadong();
                case "华北" -> Region.huabei();
                case "华南" -> Region.huanan();
                case "北美" -> Region.beimei();
                case "东南亚" -> Region.xinjiapo();
                default -> Region.autoRegion();
            };
            configuration = new Configuration(region);
            // 指定分片上传版本
            configuration.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        }
        return configuration;
    }
}
