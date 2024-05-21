package com.gewuyou.blog.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.gewuyou.blog.common.constant.MessageConstant.ID_CANNOT_BE_EMPTY;
import static com.gewuyou.blog.common.constant.MessageConstant.REVIEW_STATUS_CANNOT_BE_EMPTY;

/**
 * 评论VO
 *
 * @author gewuyou
 * @since 2024-05-20 下午10:50:17
 */
@Schema(description = "评论VO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewVO {
    /**
     * 评论Id列表
     */
    @Schema(description = "评论Id列表")
    @NotEmpty(message = ID_CANNOT_BE_EMPTY)
    private List<Long> ids;

    /**
     * 是否审核
     */
    @Schema(description = "是否审核")
    @NotEmpty(message = REVIEW_STATUS_CANNOT_BE_EMPTY)
    private Byte isReview;
}
