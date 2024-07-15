package com.gewuyou.blog.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.gewuyou.blog.common.constant.MessageConstant.*;

/**
 * 说说VO
 *
 * @author gewuyou
 * @since 2024-06-16 下午2:48:26
 */
@Schema(description = "说说VO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TalkVO {
    /**
     * 说说ID
     */
    @Schema(description = "说说ID")
    private Integer id;

    /**
     * 说说内容
     */
    @Schema(description = "说说内容")
    @NotBlank(message = TALK_CONTENT_CANNOT_BE_EMPTY)
    private String content;

    /**
     * 说说图片
     */
    @Schema(description = "说说图片")
    private String images;

    /**
     * 说说是否置顶
     */
    @Schema(description = "说说是否置顶")
    @NotNull(message = TALK_STATUS_CANNOT_BE_EMPTY)
    private Byte isTop;

    /**
     * 说说状态
     */
    @Schema(description = "说说状态")
    @NotNull(message = PINNED_STATE_CANNOT_BE_EMPTY)
    private Byte status;
}
