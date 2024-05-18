package com.gewuyou.blog.common.enums;

import lombok.Getter;

/**
 * 操作类型
 *
 * @author gewuyou
 * @since 2024-05-03 下午10:17:24
 */
@Getter
public enum OperationType {
    /**
     * 新增或修改
     */
    SAVE_OR_UPDATE,
    /**
     * 保存
     */
    SAVE,
    /**
     * 修改
     */
    UPDATE,
    /**
     * 删除
     */
    DELETE,
    /**
     * 上传
     */
    UPLOAD,
    /**
     * 导出
     */
    EXPORT,
    /**
     * 导入
     */
    IMPORT,
    /**
     * 其它
     */
    OTHER;
    private final String value;


    OperationType() {
        this.value = this.toString();
    }


    public static OperationType getEnum(String value) {
        for (OperationType operationType : OperationType.values()) {
            if (operationType.getValue().equals(value)) {
                return operationType;
            }
        }
        return OTHER;
    }
}
