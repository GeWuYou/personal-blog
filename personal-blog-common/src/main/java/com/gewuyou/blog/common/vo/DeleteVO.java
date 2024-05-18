package com.gewuyou.blog.common.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 删除 VO
 *
 * @author gewuyou
 * @since 2024-05-12 下午10:05:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteVO {

    /**
     * 主键id列表
     */
    @NotNull(message = "id不能为空")
    private List<Long> ids;

    /**
     * 状态值
     */
    @NotNull(message = "状态值不能为空")
    private Byte isDelete;
}
