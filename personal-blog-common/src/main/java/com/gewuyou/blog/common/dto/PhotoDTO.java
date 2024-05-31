package com.gewuyou.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 照片DTO
 *
 * @author gewuyou
 * @since 2024-05-31 下午2:56:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDTO {
    /**
     * 照片相册封面
     */
    private String photoAlbumCover;

    /**
     * 照片相册名称
     */
    private String photoAlbumName;

    /**
     * 照片列表
     */
    private List<String> photos;
}
