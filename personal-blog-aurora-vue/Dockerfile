FROM nginx:stable-alpine3.17
RUN mkdir -p /usr/local/aurora-vue/blog/
RUN mkdir -p /usr/local/aurora-vue/admin/
COPY personal-blog-aurora-vue/aurora-blog/dist/ /app/aurora-vue/blog/
COPY personal-blog-aurora-vue/aurora-admin/dist/ /app/aurora-vue/admin/
CMD ["nginx", "-g", "daemon off;"]
EXPOSE 80
EXPOSE 443
