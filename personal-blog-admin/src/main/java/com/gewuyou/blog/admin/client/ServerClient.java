package com.gewuyou.blog.admin.client;

import com.gewuyou.blog.admin.config.FeignClientConfiguration;
import com.gewuyou.blog.common.constant.InterfacePermissionConstant;
import com.gewuyou.blog.common.dto.ArticleRankDTO;
import com.gewuyou.blog.common.dto.ArticleStatisticsDTO;
import com.gewuyou.blog.common.dto.CategoryDTO;
import com.gewuyou.blog.common.dto.WebsiteConfigDTO;
import com.gewuyou.blog.common.entity.Result;
import com.gewuyou.blog.common.model.Tag;
import com.gewuyou.blog.common.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 服务器客户端
 *
 * @author gewuyou
 * @since 2024-04-23 下午9:55:33
 */
@FeignClient(name = "personal-blog-server", url = "http://localhost:8084" + InterfacePermissionConstant.SERVER_BASE_URL, configuration = FeignClientConfiguration.class)
public interface ServerClient {
    @DeleteMapping("/dynamic-security-metadata-source/clear")
    void clearConfigAttributeMap();

    @PostMapping("/user-info/insert")
    void userInfoInsert(UserInfo userInfo);

    @GetMapping("/user-info/count")
    Result<Long> selectUserInfoCount();

    @GetMapping("/article/count/not-deleted")
    Long selectArticleCountNotDeleted();

    @GetMapping("/article/list/statistics")
    Result<List<ArticleStatisticsDTO>> listArticleStatistics();

    @PostMapping("/article/rank")
    List<ArticleRankDTO> listArticleRank(@RequestBody Map<Object, Double> articleMap);

    @GetMapping("/category/count")
    Long selectCategoryCount();

    @GetMapping("/category/list")
    Result<List<CategoryDTO>> listCategories();

    @GetMapping("/tag/count")
    Long selectTagCount();

    @GetMapping("/tag/all")
    Result<List<Tag>> listTags();

    @GetMapping("/talk/count")
    Long selectTalkCount();

    @GetMapping("/comment/count/type/{type}")
    Long selectCommentCountByType(@PathVariable(name = "type") Byte type);

    @GetMapping("/website/config")
    Result<WebsiteConfigDTO> getWebsiteConfig();
}
