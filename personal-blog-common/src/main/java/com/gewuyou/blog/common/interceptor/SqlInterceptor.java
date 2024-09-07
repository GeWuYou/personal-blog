package com.gewuyou.blog.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;

/**
 * SQL 拦截器
 *
 * @author gewuyou
 * @since 2024-09-07 16:25:04
 */
@Slf4j
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
        }
)
public class SqlInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 计算这一次SQL执行前后的时间，统计SQL执行时间
        long startTime = System.nanoTime();
        Object proceed = invocation.proceed();
        long endTime = System.nanoTime();
        // 转换为毫秒
        long executionTime = (endTime - startTime) / 1_000_000;

        String sql = null;
        try {
            sql = generateSql(invocation);
        } catch (Exception e) {
            log.error("解析SQL语句出错", e);
        } finally {
            log.info("\nSQL执行耗时：{}ms\nSQL语句：{}", executionTime, sql);
        }
        return proceed;
    }

    /**
     * 生成SQL语句
     *
     * @param invocation Invocation 对象
     * @return SQL语句
     */
    private String generateSql(Invocation invocation) {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (Objects.nonNull(mappedStatement) && invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        // 获取MyBatis配置信息
        Configuration configuration = mappedStatement.getConfiguration();
        // 获取SQL语句及其参数
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        // 获取参数对象
        Object parameterObject = boundSql.getParameterObject();
        // 获取参数映射
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // 获取SQL语句并去除多余空格
        String sql = boundSql.getSql().replaceAll("\\s+", " ");
        // 解析sql语句替换参数
        return parsing(sql, boundSql, parameterMappings, parameterObject, configuration);
    }

    /**
     * 解析SQL语句替换参数
     *
     * @param sql               SQL语句
     * @param boundSql          绑定SQL对象
     * @param parameterMappings 参数映射
     * @param parameterObject   参数对象
     * @param configuration     MyBatis配置信息
     * @return 解析后的SQL语句
     */
    private String parsing(String sql, BoundSql boundSql, List<ParameterMapping> parameterMappings, Object parameterObject, Configuration configuration) {
        if (Objects.isNull(parameterMappings) || Objects.isNull(parameterObject)) {
            return sql;
        }
        // 获取类型处理器注册器(TypeHandler用于处理Java类型与JDBC类型之间的转换)
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        // 查看参数对象类型是否有对应的类型处理器如果有则使用类型处理器进行处理
        if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
        }
        // 否则，逐个处理参数映射
        for (ParameterMapping parameterMapping : parameterMappings) {
            // 获取参数的属性名
            String propertyName = parameterMapping.getProperty();
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            // 检查对象中是否存在该属性的getter方法如果有则调用该方法获取属性值
            if (metaObject.hasGetter(propertyName)) {
                Object obj = metaObject.getValue(propertyName);
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
            }
            // 检查BoundSql对象是否存在附加参数，附加参数可能是在动态Sql处理中生成的，如果有则获取该参数的值
            else if (boundSql.hasAdditionalParameter(propertyName)) {
                Object obj = boundSql.getAdditionalParameter(propertyName);
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
            }
            // 如果都没有说明SQL匹配不上带上缺失方便查找问题
            else {
                sql = sql.replaceFirst("\\?", "缺失参数");
            }
        }
        return sql;
    }

    /**
     * 获取参数值
     *
     * @param parameterObject 参数对象
     * @return 参数值
     */
    private String getParameterValue(Object parameterObject) {
        String value = "null";
        if (parameterObject instanceof String parameter) {
            value = "'" + parameter + "'";
        } else if (parameterObject instanceof Date parameter) {
            DateFormat format = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + format.format(parameter) + "'";
        } else if (Objects.nonNull(parameterObject)) {
            value = parameterObject.toString();
        }
        return value;
    }
}
