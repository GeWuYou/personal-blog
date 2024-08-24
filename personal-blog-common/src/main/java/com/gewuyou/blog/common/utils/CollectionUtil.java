package com.gewuyou.blog.common.utils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合 工具类
 *
 * @author gewuyou
 * @since 2024-07-13 下午6:12:45
 */
public class CollectionUtil {
    /**
     * 将Map集合并按照ID设置分组并转换为List
     *
     * @param parentList        父节点集合
     * @param childrenMap       子节点集合
     * @param getId             获取子节点的ID方法
     * @param setChildren       设置子节点方法
     * @param <T>               对象类型
     * @param <ID>              ID类型
     * @param isRemoveProcessed 是否移除已经处理过的子节点
     * @return 分组后的集合
     */
    public static <T, ID> List<T> processItemsWithChildren(
            List<T> parentList,
            Map<ID, List<T>> childrenMap,
            Function<T, ID> getId,
            BiConsumer<T, List<T>> setChildren,
            boolean isRemoveProcessed) {
        var result = parentList.stream()
                .peek(item -> {
                    var children = childrenMap.get(getId.apply(item));
                    if (Objects.nonNull(children)) {
                        setChildren.accept(item, children);
                    } else {
                        setChildren.accept(item, List.of());
                    }
                })
                .collect(Collectors.toList());
        if (isRemoveProcessed) {
            removeProcessedChildren(parentList, childrenMap, getId);
        }
        return result;
    }

    /**
     * 移除已经处理过的子节点
     *
     * @param parentList  父节点集合
     * @param childrenMap 子节点集合
     * @param getId       获取子节点的ID方法
     * @param <T>         值类型
     * @param <ID>        ID类型
     */
    private static <T, ID> void removeProcessedChildren(
            List<T> parentList,
            Map<ID, List<T>> childrenMap,
            Function<T, ID> getId) {
        parentList.forEach(item -> childrenMap.remove(getId.apply(item)));
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return result;
    }

    public static <T> Set<T> castSet(Object obj, Class<T> clazz) {
        Set<T> result = new HashSet<>();
        if (obj instanceof Set<?>) {
            for (Object o : (Set<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return result;
    }

    /**
     * 集合差集
     *
     * @param c1  集合1
     * @param c2  集合2
     * @param <E> 元素类型
     * @return 差集
     * @apiNote 求集合1和2的差集即为集合1中存在而集合2中不存在的元素
     */
    public static <E> Collection<E> difference(Collection<E> c1, Collection<E> c2) {
        return c1.stream().filter(e -> !c2.contains(e)).collect(Collectors.toList());
    }
}
