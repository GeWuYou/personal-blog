package com.gewuyou.blog.common.utils;

import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BeanCopyUtil
 *
 * @author gewuyou
 * @since 2024-04-26 下午8:44:42
 */
@Slf4j
public class BeanCopyUtil {

    /**
     * 将源对象的属性复制到目标类的新实例中
     *
     * @param source 要从中复制的对象
     * @param target 要创建并复制到的对象的类
     * @param <T>    目标对象的类型
     * @return 具有 Copy 属性的目标类的新实例
     * @throws GlobalException 如果复制过程失败，则抛出此异常
     */
    public static <T> T copyObject(Object source, Class<T> target) {
        T temp;
        try {
            temp = target.getDeclaredConstructor().newInstance();
            if (Objects.nonNull(source)) {
                org.springframework.beans.BeanUtils.copyProperties(source, temp);
            }
        } catch (Exception e) {
            log.error("详细信息：{}", ResponseInformation.OBJECT_COPY_FAILED.getMessage(), e);
            throw new GlobalException(ResponseInformation.OBJECT_COPY_FAILED);
        }
        return temp;
    }

    /**
     * 将对象列表从源列表复制到新的目标对象列表。
     *
     * @param source 要从中复制的源对象列表
     * @param target 要复制到的目标对象的类
     * @param <T>    目标对象的类型
     * @param <S>    源对象的类型
     * @return 从源列表中复制的目标对象的新列表
     */
    public static <T, S> List<T> copyList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (Objects.nonNull(source) && !source.isEmpty()) {
            for (Object obj : source) {
                list.add(BeanCopyUtil.copyObject(obj, target));
            }
        }
        return list;
    }
}
