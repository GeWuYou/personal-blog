package com.gewuyou.blog.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gewuyou.blog.common.constant.RegularConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class FileUtil {


    /**
     * 获取文件的MD5值
     *
     * @param inputStream 文件输入流
     * @return MD5值
     */
    public static String getMd5(InputStream inputStream) {
        try (inputStream) {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(md5.digest()));
        } catch (Exception e) {
            log.error("getMd5 error", e);
            return null;
        }
    }

    /**
     * 获取文件的扩展名
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getExtName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 将MultipartFile转化为File
     *
     * @param multipartFile MultipartFile
     * @return File
     */
    public static File multipartFileToFile(MultipartFile multipartFile) {
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = Objects.requireNonNull(originalFilename).split("\\.");
            file = File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            log.error("multipartFileToFile error", e);
        }
        return file;
    }

    /**
     * 获取文件的网络路径获取文件路径
     *
     * @param url 网络路径
     * @return 文件路径
     */
    public static String getFilePathByUrl(String url) {
        Pattern pattern = Pattern.compile(RegularConstant.FILE_PATH_REGEX);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    /**
     * 获取文件的准确大小
     *
     * @param size 文件大小
     * @return 文件大小
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2048) {
            accuracy = 0.6;
        } else if (size < 3072) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }

    public static void main(String[] args) {
        String url = "http://localhost:8082/api/v1/admin/blog/articles/1f5af4f1d3fa2229ee66f37bf95ef903.jpg";
        String regex = "^https?://[^/]+/api/v1/admin(.*)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            String result = matcher.group(1);
            System.out.println(result);  // 输出: blog/articles/1f5af4f1d3fa2229ee66f37bf95ef903.jpg
        } else {
            System.out.println("No match found");
        }
    }

}
