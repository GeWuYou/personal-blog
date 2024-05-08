package com.gewuyou.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 标签选项 DTO
 *
 * @author gewuyou
 * @since 2024-05-04 下午3:17:17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabelOptionDTO {
    private Integer id;

    private String label;

    private List<LabelOptionDTO> children;
}
