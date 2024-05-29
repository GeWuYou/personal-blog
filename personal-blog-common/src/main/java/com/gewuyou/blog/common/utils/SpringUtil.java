package com.gewuyou.blog.common.utils;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Spring 工具
 *
 * @author gewuyou
 * @since 2024-05-29 下午2:50:39
 */
@Component
public class SpringUtil implements BeanFactoryPostProcessor, ApplicationContextAware {

    private static ConfigurableListableBeanFactory beanFactory;

    private static ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtil.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    /**
     * 获取 Bean
     *
     * @param name  Bean 名称
     * @param clazz Bean 类型
     * @param <T>   Bean 类型
     * @return Bean
     * @throws BeansException 异常
     */
    public static <T> T getBean(String name, Class<T> clazz) throws BeansException {
        return beanFactory.getBean(name, clazz);
    }

    /**
     * 获取 Bean
     *
     * @param clazz Bean 类型
     * @param <T>   Bean 类型
     * @return Bean
     * @throws BeansException 异常
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return beanFactory.getBean(clazz);
    }

    /**
     * 判断是否存在 Bean
     *
     * @param name Bean 名称
     * @return true：存在；false：不存在
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断是否为单例 Bean
     *
     * @param name Bean 名称
     * @return true：单例；false：多例
     * @throws NoSuchBeanDefinitionException 异常
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    /**
     * 获取 Bean 类型
     *
     * @param name Bean 名称
     * @return Bean 类型
     * @throws NoSuchBeanDefinitionException 异常
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    /**
     * 获取指定名称的 bean 的别名数组。
     *
     * @param name Bean 名称
     * @return 别名数组
     * @throws NoSuchBeanDefinitionException 异常
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

    /**
     * 获取当前 AOP 代理对象，用于获取 Spring 中被 AOP 代理的 bean。
     *
     * @param clazz Bean 类型
     * @param <T>   Bean 类型
     * @return Bean
     */
    public static <T> T getAopProxy(Class<T> clazz) {
        return clazz.cast(AopContext.currentProxy());
    }

    /**
     * 获取当前激活的 Spring profiles 数组。
     *
     * @return profiles 数组
     */
    public static String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前激活的 Spring profile 名称，如果没有激活的 profile，则返回 null。
     *
     * @return profile 名称
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return activeProfiles.length > 0 ? activeProfiles[0] : null;
    }
}
