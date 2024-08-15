package com.gewuyou.blog.admin.util;

import com.gewuyou.blog.common.model.Job;
import com.gewuyou.blog.common.utils.SpringUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * 作业调用工具
 *
 * @author gewuyou
 * @since 2024-05-29 下午3:33:10
 */
public class JobInvokeUtil {
    private JobInvokeUtil() {
    }

    /**
     * 根据给定的作业配置，调用指定的Bean或类的方法。
     *
     * @param job 作业对象，包含了需要调用的方法的信息
     * @throws Exception 如果方法调用过程中出现异常
     */
    public static void invokeMethod(Job job) throws Exception {
        // 获取调用目标字符串
        String invokeTarget = job.getInvokeTarget();
        // 解析出Bean名称或类名
        String beanName = getBeanName(invokeTarget);
        // 解析出方法名称
        String methodName = getMethodName(invokeTarget);
        // 解析出方法参数列表
        List<Object[]> methodParams = getMethodParams(invokeTarget);

        // 如果解析出的名称不是类名，则从Spring上下文中获取Bean实例
        if (!isValidClassName(beanName)) {
            Object bean = SpringUtil.getBean(beanName, Object.class);
            // 反射调用Bean的方法
            invokeMethod(bean, methodName, methodParams);
        } else {
            // 如果是类名，则通过反射实例化该类，并调用其方法
            Object bean = Class.forName(beanName).getDeclaredConstructor().newInstance();
            // 反射调用类的方法
            invokeMethod(bean, methodName, methodParams);
        }
    }

    /**
     * 使用反射调用指定对象的指定方法。
     *
     * @param bean         要调用方法的对象实例
     * @param methodName   方法名称
     * @param methodParams 方法参数列表
     * @throws NoSuchMethodException     方法未找到时抛出
     * @throws IllegalAccessException    方法访问权限不足时抛出
     * @throws InvocationTargetException 方法调用出错时抛出
     */
    private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        if (methodParams != null && !methodParams.isEmpty()) {
            // 如果有方法参数，根据参数类型调用对应的方法
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
            method.invoke(bean, getMethodParamsValue(methodParams));
        } else {
            // 如果没有方法参数，直接调用无参方法
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    /**
     * 判断给定的目标字符串是否为有效的类名。
     * 有效类名的判定标准是包含至少两个 "." 字符。
     *
     * @param invokeTarget 调用目标字符串
     * @return 如果是类名则返回true，否则返回false
     */
    public static boolean isValidClassName(String invokeTarget) {
        return StringUtils.countMatches(invokeTarget, ".") > 1;
    }

    /**
     * 从调用目标字符串中解析出Bean的名称或类名。
     *
     * @param invokeTarget 调用目标字符串
     * @return 解析出的Bean名称或类名
     */
    public static String getBeanName(String invokeTarget) {
        // 提取出方法名前的部分
        String beanName = StringUtils.substringBefore(invokeTarget, "(");
        // 提取出最后一个"."之前的部分作为Bean名称
        return StringUtils.substringBeforeLast(beanName, ".");
    }

    /**
     * 从调用目标字符串中解析出方法名称。
     *
     * @param invokeTarget 调用目标字符串
     * @return 解析出的方法名称
     */
    public static String getMethodName(String invokeTarget) {
        // 提取出方法名前的部分
        String methodName = StringUtils.substringBefore(invokeTarget, "(");
        // 提取出最后一个"."之后的部分作为方法名称
        return StringUtils.substringAfterLast(methodName, ".");
    }

    /**
     * 从调用目标字符串中解析出方法参数列表。
     *
     * @param invokeTarget 调用目标字符串
     * @return 方法参数列表，每个参数包含一个Object数组，其中第一个元素是参数值，第二个元素是参数类型
     */
    public static List<Object[]> getMethodParams(String invokeTarget) {
        // 提取出括号内的参数字符串
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        // 如果参数字符串为空，返回null
        if (StringUtils.isEmpty(methodStr)) {
            return null;
        }
        // 以逗号分隔参数
        String[] methodParams = methodStr.split(",");
        // 存储参数的类型和值
        List<Object[]> classes = new LinkedList<>();
        for (String methodParam : methodParams) {
            String str = StringUtils.trimToEmpty(methodParam);
            // 处理字符串参数
            if (StringUtils.contains(str, "'")) {
                classes.add(new Object[]{StringUtils.replace(str, "'", ""), String.class});
            }
            // 处理布尔类型参数
            else if (StringUtils.equals(str, "true") || StringUtils.equalsIgnoreCase(str, "false")) {
                classes.add(new Object[]{Boolean.valueOf(str), Boolean.class});
            }
            // 处理长整型参数
            else if (StringUtils.containsIgnoreCase(str, "L")) {
                classes.add(new Object[]{Long.valueOf(StringUtils.replaceIgnoreCase(str, "L", "")), Long.class});
            } // 处理双精度浮点型参数
            else if (StringUtils.containsIgnoreCase(str, "D")) {
                classes.add(new Object[]{Double.valueOf(StringUtils.replaceIgnoreCase(str, "D", "")), Double.class});
            }
            // 处理整型参数
            else {
                classes.add(new Object[]{Integer.valueOf(str), Integer.class});
            }
        }
        return classes; // 返回参数列表
    }

    /**
     * 从方法参数列表中提取参数类型数组。
     *
     * @param methodParams 方法参数列表
     * @return 参数类型数组
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        Class<?>[] classes = new Class<?>[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            classes[index] = (Class<?>) os[1];
            index++;
        }
        return classes;
    }

    /**
     * 从方法参数列表中提取参数值数组。
     *
     * @param methodParams 方法参数列表
     * @return 参数值数组
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
        Object[] classes = new Object[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            // 提取每个参数的值
            classes[index] = os[0];
            index++;
        }
        return classes;
    }
}
