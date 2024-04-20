package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gewuyou.blog.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    Optional<User> selectByUserName(@Param("userName") String userName);

    Optional<User> selectByEmail(@Param("email") String email);

    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);
}
