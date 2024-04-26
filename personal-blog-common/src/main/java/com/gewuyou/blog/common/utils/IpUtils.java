package com.gewuyou.blog.common.utils;

import com.gewuyou.blog.common.constant.CommonConstant;
import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ip工具类
 *
 * @author gewuyou
 * @since 2024-04-17 下午8:33:33
 */
@Slf4j
@Component
public class IpUtils {
    private static DbSearcher searcher;

    private static Method method;

    private IpUtils() {
    }

    private static final String UNKNOWN = "unknown";

    /**
     * 获取当前网络ip
     *
     * @param request 请求
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.isEmpty() || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    throw new GlobalException(ResponseInformation.ERR_GET_LOCAL_IP_FAILED);
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            int start = ipAddress.indexOf(",");
            //"***.***.***.***".length() = 15
            if (start > 0) {
                ipAddress = ipAddress.substring(0, start);
            }
        }
        return ipAddress;
    }


    /**
     * 获得MAC地址
     *
     * @param ip ip地址
     * @return MAC地址
     */
    public static String getMACAddress(String ip) {
        String str;
        String macAddress = "";
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null && str.indexOf("MAC Address") > 1) {
                    macAddress = str.substring(str.indexOf("MAC Address") + 14);
                    break;
                }
            }
        } catch (IOException e) {
            log.error("获取MAC地址失败", e);
        }
        return macAddress;
    }

    /**
     * 获取访问设备
     *
     * @param request 请求
     * @return {@link UserAgent} 访问设备
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }

    /**
     * 解析ip地址
     *
     * @param ipAddress ip地址
     * @return 解析后的ip地址
     */
    public static String getIpSource(String ipAddress) {
        if (ipAddress == null || !Util.isIpAddress(ipAddress)) {
            log.error("Error: Invalid ip address");
            return "";
        }
        try {
            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ipAddress);
            String ipInfo = dataBlock.getRegion();
            if (!StringUtils.isEmpty(ipInfo)) {
                ipInfo = ipInfo.replace("|0", "");
                ipInfo = ipInfo.replace("0|", "");
                return ipInfo;
            }
        } catch (Exception e) {
            log.error("getCityInfo exception:", e);
        }
        return "";
    }

    /**
     * 获取ip的省份
     *
     * @param ipSource ip地址
     * @return ip的省份
     */
    public static String getIpProvince(String ipSource) {
        if (StringUtils.isBlank(ipSource)) {
            return CommonConstant.UNKNOWN;
        }
        String[] strings = ipSource.split("\\|");
        if (strings.length > 1 && strings[1].endsWith("省")) {
            return StringUtils.substringBefore(strings[1], "省");
        }
        return strings[0];
    }

    /**
     * 初始化ip2region资源
     *
     * @throws Exception 异常
     */
    @PostConstruct
    private void initIp2regionResource() throws Exception {
        InputStream inputStream = new ClassPathResource("/ip/ip2region.db").getInputStream();
        byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
        DbConfig dbConfig = new DbConfig();
        searcher = new DbSearcher(dbConfig, dbBinStr);
        method = searcher.getClass().getMethod("memorySearch", String.class);
    }
}
