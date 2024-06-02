package com.gewuyou.blog.admin.client;

import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.ArticleRankDTO;
import com.gewuyou.blog.common.dto.ArticleStatisticsDTO;
import com.gewuyou.blog.common.dto.CategoryDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.model.Tag;
import com.gewuyou.blog.common.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 服务器客户端
 *
 * @author gewuyou
 * @since 2024-04-23 下午9:55:33
 */
@FeignClient(name = "personal-blog-server", url = "http://localhost:8084" + InterfacePermissionConstant.SERVER_BASE_URL)
public interface ServerClient {
    @PostMapping("/user-info/insert")
    void userInfoInsert(UserInfo userInfo);

    @PostMapping("/user-info/count")
    Long selectUserInfoCount();

    @GetMapping("/user-info/{id}")
    UserInfo selectUserInfoById(@PathVariable(name = "id") Long id);

    @GetMapping("/article/count/not-deleted")
    Long selectArticleCountNotDeleted();

    @GetMapping("/article/statistics")
    List<ArticleStatisticsDTO> listArticleStatistics();

    @PostMapping("/article/rank")
    List<ArticleRankDTO> listArticleRank(@RequestBody Map<Object, Double> articleMap);

    @GetMapping("/category/count")
    Long selectCategoryCount();

    @GetMapping("/category/list")
    List<CategoryDTO> listCategories();

    @GetMapping("/tag/count")
    Long selectTagCount();

    @GetMapping("/tag/all")
    Result<List<Tag>> listTags();

    @GetMapping("/talk/count")
    Long selectTalkCount();

    @GetMapping("/comment/count/type/{type}")
    Long selectCommentCountByType(@PathVariable(name = "type") Byte type);
}
