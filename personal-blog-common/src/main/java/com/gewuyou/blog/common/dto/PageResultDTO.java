package com.gewuyou.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 页面结果 DTO
 *
 * @author gewuyou
 * @since 2024-04-23 下午10:53:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResultDTO<T> {
    /**
     * 记录
     */
    private List<T> records;

    /**
     * 总记录数
     */
    private Long count;
}
