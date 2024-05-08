package com.gewuyou.blog.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IsHiddenVO
 *
 * @author gewuyou
 * @since 2024-05-03 下午8:35:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsHiddenVO {
    private Integer id;

    private Integer isHidden;
}
