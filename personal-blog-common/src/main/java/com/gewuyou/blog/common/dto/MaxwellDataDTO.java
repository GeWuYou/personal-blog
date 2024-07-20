package com.gewuyou.blog.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Maxwell 数据 DTO
 * <p>
 * 这个类用于封装由 Maxwell 捕获的 MySQL 数据库变更信息。Maxwell 是一个实时捕获 MySQL 数据库变更的工具，
 * 可以将这些变更转换为 JSON 格式并发送到消息队列或流处理系统中。
 * </p>
 * <ul>
 *     <li>database: 数据变更发生的数据库名称</li>
 *     <li>xid: 事务 ID，用于标识变更属于哪个事务</li>
 *     <li>data: 变更的数据内容，以键值对的形式存储</li>
 *     <li>commit: 事务提交状态，表示该变更是否已提交</li>
 *     <li>type: 变更类型，例如 "insert"、"update"、"delete"</li>
 *     <li>table: 变更发生的表名</li>
 *     <li>ts: 时间戳，表示变更发生的时间</li>
 * </ul>
 *
 * @author gewuyou
 * @since 2024-07-20 下午3:51:14
 */
@Schema(description = "Maxwell 数据 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaxwellDataDTO {
    /**
     * 数据变更发生的数据库名称
     */
    @Schema(description = "数据变更发生的数据库名称")
    private String database;

    /**
     * 事务 ID，用于标识变更属于哪个事务
     */
    @Schema(description = "事务 ID，用于标识变更属于哪个事务")
    private Integer xid;

    /**
     * 变更的数据内容，以键值对的形式存储
     */
    @Schema(description = "变更的数据内容，以键值对的形式存储")
    private Map<String, Object> data;

    /**
     * 事务提交状态，表示该变更是否已提交
     */
    @Schema(description = "事务提交状态，表示该变更是否已提交")
    private Boolean commit;

    /**
     * 变更类型，例如 "insert"、"update"、"delete"
     */
    @Schema(description = "变更类型，例如  \"insert\"、\"update\"、\"delete\"")
    private String type;

    /**
     * 变更发生的表名
     */
    @Schema(description = "变更发生的表名")
    private String table;

    /**
     * 时间戳，表示变更发生的时间
     */
    @Schema(description = "时间戳，表示变更发生的时间")
    private Integer ts;
}
