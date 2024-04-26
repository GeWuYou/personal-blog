package com.gewuyou.blog.common.utils;

import com.gewuyou.blog.common.enums.ResponseInformation;
import com.gewuyou.blog.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * BeanCopyUtils
 *
 * @author gewuyou
 * @since 2024-04-26 下午8:44:42
 */
@Slf4j
public class BeanCopyUtils {
    public static <T> T copyObject(Object source, Class<T> target) {
        T temp;
        try {
            temp = target.getDeclaredConstructor().newInstance();
            if (null != source) {
                org.springframework.beans.BeanUtils.copyProperties(source, temp);
            }
        } catch (Exception e) {
            throw new GlobalException(ResponseInformation.OBJECT_COPY_FAILED);
        }
        return temp;
    }

    public static <T, S> List<T> copyList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (null != source && !source.isEmpty()) {
            for (Object obj : source) {
                list.add(BeanCopyUtils.copyObject(obj, target));
            }
        }
        return list;
    }
}
