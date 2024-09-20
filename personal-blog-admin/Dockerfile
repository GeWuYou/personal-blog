# 从openjdk:17镜像开始构建
FROM openjdk:17
# 作者信息
LABEL authors="gewuyou"
# 版本
ENV version="0.1.0"
# 设置工作目录
WORKDIR /app
# 设置环境变量 设置激活生产环境配置
ENV SPRING_PROFILES_ACTIVE=prod
# 将项目的jar包复制到镜像中
COPY target/personal-blog-admin-$version.jar /app/personal-blog-admin.jar
#COPY personal-blog-admin-$version.jar /app/personal-blog-admin.jar
# 设置数据卷
VOLUME /app/logs
# 暴露应用端口
EXPOSE 8081
# 设置容器创建时的主要命令
ENTRYPOINT ["java", "-Xms256m","-Xmx512m","-XX:+UseContainerSupport","-Duser.timezone=Asia/Shanghai","-jar","/app/personal-blog-admin.jar"]