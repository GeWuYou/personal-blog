package com.gewuyou.blog.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基础类
 *
 * @author gewuyou
 * @since 2024-04-13 下午2:32:42
 */
@Data
public class QueryPageBaseBean implements Serializable {
    /**
     * 当前页码
     */
    private Integer currentPage;
    /**
     * 每页记录数
     */
    private Integer pageSize;
    /**
     * 排序字段
     */
    private String orderBy;

    public QueryPageBaseBean() {
        this.currentPage = 1;
        this.pageSize = 10;
        this.orderBy = "id";
    }

}
