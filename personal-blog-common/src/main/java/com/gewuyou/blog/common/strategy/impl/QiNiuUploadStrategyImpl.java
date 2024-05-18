package com.gewuyou.blog.common.strategy.impl;

import java.io.InputStream;

/**
 * 七牛云上传策略实现
 * todo 待实现
 *
 * @author gewuyou
 * @since 2024-05-18 下午8:19:42
 */
public class QiNiuUploadStrategyImpl extends AbstractUploadStrategyImpl {
    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return true：存在；false：不存在
     */
    @Override
    public Boolean exists(String filePath) {
        return null;
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

    }

    /**
     * 获取文件访问地址
     *
     * @param filePath 文件路径
     * @return 文件访问地址
     */
    @Override
    public String getFileAccessUrl(String filePath) {
        return "";
    }
}
