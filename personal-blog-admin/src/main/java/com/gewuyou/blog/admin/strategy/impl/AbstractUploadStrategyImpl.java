package com.gewuyou.blog.admin.strategy.impl;

import com.gewuyou.blog.admin.strategy.interfaces.UploadStrategy;
import com.gewuyou.blog.common.constant.RedisConstant;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import com.gewuyou.blog.common.service.IRedisService;
import com.gewuyou.blog.common.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * 抽象上传策略实现
 *
 * @author gewuyou
 * @since 2024-05-05 下午9:31:03
 */
@Slf4j
@Service
public abstract class AbstractUploadStrategyImpl implements UploadStrategy {
    private final IRedisService redisService;

    private final Executor asyncTaskExecutor;

    public AbstractUploadStrategyImpl(IRedisService redisService, @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutoExecutor) {
        this.redisService = redisService;
        this.asyncTaskExecutor = asyncTaskExecutoExecutor;
    }

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @param path 上传路径
     * @return 上传后的路径
     */
    @Override
    public CompletableFuture<String> uploadFile(MultipartFile file, String path) {
        try {
            String md5 = FileUtil.getMd5(file.getInputStream());
            String extName = FileUtil.getExtName(file.getOriginalFilename());
            String fileName = md5 + "-" + LocalDateTime.now().toString().replace(":", "-") + extName;
            // 处理上传文件
            return CompletableFuture.runAsync(() -> {
                if (!exists(path + fileName)) {
                    try {
                        upload(path, fileName, file.getInputStream());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                log.info("文件上传成功，文件名：{}", fileName);
            }, asyncTaskExecutor).exceptionally(e -> {
                log.error("文件上传失败", e);
                throw new GlobalException(ResponseInformation.FILE_UPLOAD_FAILED);
            }).thenApply(v -> {
                String fileAccessUrl = getFileAccessUrl(path + fileName);
                // 保存文件名到redis
                redisService.sAdd(RedisConstant.TEMP_IMAGE_NAME, fileAccessUrl);
                // 返回访问地址
                return fileAccessUrl;
            });
        } catch (Exception e) {
            log.error("获取文件MD5失败", e);
            throw new GlobalException(ResponseInformation.FILE_UPLOAD_FAILED);
        }
    }

    /**
     * 上传文件
     *
     * @param fileName    上传的文件名
     * @param inputStream 上传的文件流
     * @param path        上传路径
     * @return 上传后的路径
     */
    @Override
    public CompletableFuture<String> uploadFile(String fileName, InputStream inputStream, String path) {
        return CompletableFuture.runAsync(() -> {
            try {
                upload(path, fileName, inputStream);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, asyncTaskExecutor).exceptionally(e -> {
            log.error("文件上传失败", e);
            throw new GlobalException(ResponseInformation.FILE_UPLOAD_FAILED);
        }).thenApply(v -> getFileAccessUrl(path + fileName));
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return true：存在；false：不存在
     */
    public abstract Boolean exists(String filePath);

    /**
     * 上传文件
     *
     * @param path        上传路径
     * @param fileName    上传文件名
     * @param inputStream 上传文件流
     * @throws Exception 异常
     */
    public abstract void upload(String path, String fileName, InputStream inputStream) throws Exception;

    /**
     * 获取文件访问地址
     *
     * @param filePath 文件路径
     * @return 文件访问地址
     */
    public abstract String getFileAccessUrl(String filePath);

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public abstract void delete(String filePath);
}
