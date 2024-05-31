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
    /**
     * 标签选项 ID
     */
    private Integer id;

    /**
     * 标签选项名称
     */
    private String label;

    /**
     * 标签选项子选项
     */
    private List<LabelOptionDTO> children;
}
