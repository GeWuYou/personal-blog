// package com.gewuyou.blog.admin.mapper;
//
// import com.gewuyou.blog.common.model.UserAuth;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
//
// import java.util.Optional;
//
// @SpringBootTest
// class UserAuthMapperTest {
//
//
//     @Autowired
//     private UserAuthMapper userAuthMapper;
//
//     @Test
//     void selectByUsername() {
//         Optional<UserAuth> admin = userAuthMapper.selectByUsername("admin");
//         admin.ifPresent(System.out::println);
//     }
//
//     @Test
//     void selectByEmail() {
//     }
// }