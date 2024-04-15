package com.gewuyou.blog.common.entity;

import lombok.Data;

/**
 * 分页元数据
 *
 * @author gewuyou
 * @since 2024-04-13 下午2:44:01
 */
@Data
public class PagedMetaData {
    /**
     * 总记录数
     */
    private int totalCount;
    /**
     * 每页显示的记录数
     */
    private int pageSize;
    /**
     * 当前页码
     */
    private int currentPage;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 是否有上一页
     */
    private boolean hasPrevious;

    /**
     * 是否有下一页
     */
    private boolean hasNext;

    public PagedMetaData(int currentPage, int pageSize, int totalCount) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = (totalCount + pageSize - 1) / pageSize;
        this.hasPrevious = currentPage > 1;
        this.hasNext = currentPage < totalPage;
    }
}
