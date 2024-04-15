package com.gewuyou.blog.common.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * 代码生成器
 *
 * @author gewuyou
 * @since 2024-04-13 下午7:48:59
 */
public class CodeGenerator {

    private static final String JDBC_URL = "jdbc:mysql://192.168.200.129:3306/personal_blog?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";
    private static final String AUTHOR = "gewuyou";
    private static final String PARENT_PACKAGE = "com.gewuyou.blog";

    /**
     * 代码生成器
     *
     * @param outputDir    输出目录
     * @param xmlOutputDir xml输出目录
     * @param moduleName   模块名
     * @apiNote
     * @since 2024/4/13 下午9:37
     */
    public static void generation(String outputDir, String xmlOutputDir, String moduleName) {
        FastAutoGenerator.create(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)
                // 全局配置
                .globalConfig(builder ->
                        builder
                                // 设置作者
                                .author(AUTHOR)
                                // 开启 swagger 模式
                                .enableSwagger()
                                // 指定输出目录
                                .outputDir(outputDir)
                                // 禁止打开输出目录
                                .disableOpenDir()
                )
                // 数据库配置
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler(
                                (globalConfig, typeRegistry, metaInfo) -> {
                                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                                    if (typeCode == Types.SMALLINT) {
                                        // 自定义类型转换
                                        return DbColumnType.INTEGER;
                                    }
                                    return typeRegistry.getColumnType(metaInfo);
                                }))
                // 包配置
                .packageConfig(builder ->
                        builder
                                // 设置父包名
                                .parent(PARENT_PACKAGE)
                                // 设置父包模块名
                                .moduleName(moduleName)
                                // 设置mapperXml生成路径
                                .pathInfo(Collections.singletonMap(OutputFile.xml, xmlOutputDir))
                )
                // 策略配置
                .strategyConfig(builder -> {
                    // 设置需要生成的表名
                    builder
                            .addInclude("tb_user")
                            // 设置过滤表前缀
                            .addTablePrefix("tb_")
                            // 实体配置
                            .entityBuilder()
                            .enableFileOverride()
                            // 开启lombok插件
                            .enableLombok()
                            // 服务配置
                            .serviceBuilder()
                            .enableFileOverride()
                            // 控制器配置
                            .controllerBuilder()
                            // 设置rest风格
                            .enableRestStyle()
                            .enableFileOverride()
                            .mapperBuilder()
                            .enableFileOverride()
                    ;
                })
                // 模板引擎配置
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    private static void serverCodeGeneration() {
        String outputDir = "personal-blog-server\\src\\main\\java";
        String xmlOutputDir = "personal-blog-server\\src\\main\\resources\\mapper";
        String moduleName = "server";
        generation(outputDir, xmlOutputDir, moduleName);
    }
    public static void main(String[] args) {
        serverCodeGeneration();
    }
}
