package com.gewuyou.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访问量统计 DTO
 *
 * @author gewuyou
 * @since 2024-05-04 下午11:00:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniqueViewDTO {
    /**
     * 天
     */
    private String day;

    /**
     * 访问量
     */
    private Integer viewsCount;
}
