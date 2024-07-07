package com.gewuyou.blog.common.utils;

import com.gewuyou.blog.common.dto.UserDetailsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 用户 工具类
 *
 * @author gewuyou
 * @since 2024-04-30 下午10:31:37
 */
@Component
public class UserUtil {
    public static UserDetailsDTO getUserDetailsDTO() {
        return (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
