package com.gewuyou.blog.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gewuyou.blog.common.dto.UserAdminDTO;
import com.gewuyou.blog.common.model.UserAuth;
import com.gewuyou.blog.common.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 用户认证信息表 Mapper 接口
 * </p>
 *
 * @author gewuyou
 * @since 2024-04-21
 */
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuth> {
    Optional<UserAuth> selectByUsername(@Param("username") String username);

    Optional<UserAuth> selectByEmail(@Param("email") String email);

    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    Integer countUsers(@Param("conditionVO") ConditionVO conditionVO);

    List<UserAdminDTO> listUsers(Page<UserAdminDTO> page, ConditionVO conditionVO);
}
