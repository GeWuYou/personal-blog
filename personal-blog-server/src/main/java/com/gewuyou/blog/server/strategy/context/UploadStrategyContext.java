package com.gewuyou.blog.server.strategy.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 上传策略上下文
 *
 * @author gewuyou
 * @since 2024-05-08 下午9:13:20
 */
@Service
public class UploadStrategyContext {

    @Value("${upload.mode}")
    private String uploadMode;
}
