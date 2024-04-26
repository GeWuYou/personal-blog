package com.gewuyou.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户区域 DTO
 *
 * @author gewuyou
 * @since 2024-04-22 下午9:23:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAreaDTO {
    private String name;

    private Long value;
}
