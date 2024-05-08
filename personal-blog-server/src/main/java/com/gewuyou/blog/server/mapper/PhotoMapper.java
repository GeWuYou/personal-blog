package com.gewuyou.blog.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gewuyou.blog.common.model.Photo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 照片 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-23
 */
@Mapper
public interface PhotoMapper extends BaseMapper<Photo> {

}
