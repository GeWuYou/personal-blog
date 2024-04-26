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
    private List<T> records;

    private Integer count;
}
