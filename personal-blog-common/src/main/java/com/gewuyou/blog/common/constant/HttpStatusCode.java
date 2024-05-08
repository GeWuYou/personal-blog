package com.gewuyou.blog.common.constant;

/**
 * Http 状态代码
 *
 * @author gewuyou
 * @since 2024-04-27 下午7:15:17
 */
public class HttpStatusCode {
    private HttpStatusCode() {
    }
    // 1xx (信息)
    /**
     * 继续: 服务器已收到请求标头，客户端应继续发送请求正文。
     */
    public static final int CONTINUE = 100;

    /**
     * 交换协议：服务器已接受请求，客户端应切换到其他协议。
     */
    public static final int SWITCHING_PROTOCOLS = 101;

    /**
     * 正在处理中：服务器仍在处理请求，尚未完成请求。
     */
    public static final int PROCESSING = 102;

    // 2xx (成功)
    /**
     * OK: 请求已成功。
     */
    public static final int OK = 200;

    /**
     * 已创建：请求已满足，因此已创建新资源。
     */
    public static final int CREATED = 201;

    /**
     * 已接受：请求已被接受处理，但处理尚未完成。
     */
    public static final int ACCEPTED = 202;

    /**
     * 无内容：服务器已成功完成请求，但响应有效负载正文中没有要发送的其他内容。
     */
    public static final int NO_CONTENT = 204;

    /**
     * 部分内容：服务器已满足对资源的部分 GET 请求。
     */
    public static final int PARTIAL_CONTENT = 206;

    // 3xx (重定向)
    /**
     * 多项选择：服务器对客户端可能遵循的资源有多种选择。
     */
    public static final int MULTIPLE_CHOICES = 300;

    /**
     * 永久移动：资源已永久移动到新 URL。
     */
    public static final int MOVED_PERMANENTLY = 301;

    /**
     * 已找到：已找到请求的资源，但已暂时移动到其他 URL。
     */
    public static final int FOUND = 302;

    /**
     * 未修改：请求的资源自上次请求以来未被修改。
     */
    public static final int NOT_MODIFIED = 304;

    /**
     * 临时重定向：请求的资源暂时驻留在不同的 URL 下。
     */
    public static final int TEMPORARY_REDIRECT = 307;

    /**
     * 永久重定向：请求的资源已永久移动到其他 URL。
     */
    public static final int PERMANENT_REDIRECT = 308;

    // 4xx (客户端错误)

    /**
     * 错误请求：请求有语法错误或无法被服务器理解。
     */
    public static final Integer BAD_REQUEST = 400;

    /**
     * 未经授权：用户未通过身份验证，无权访问请求的资源。
     */
    public static final Integer UNAUTHORIZED = 401;

    /**
     * 禁止：服务器理解请求，但拒绝执行。
     */
    public static final Integer FORBIDDEN = 403;

    /**
     * 未找到：请求的资源不存在。
     */
    public static final Integer NOT_FOUND = 404;

    /**
     * 方法不允许：对于请求的资源，请求方法被禁止。
     */
    public static final int METHOD_NOT_ALLOWED = 405;

    /**
     * 请求超时：服务器在等待请求时超时。
     */
    public static final int REQUEST_TIMEOUT = 408;

    /**
     * 冲突：由于与目标资源的当前状态冲突，无法完成请求。
     */
    public static final int CONFLICT = 409;

    /**
     * 消失：请求的资源不再可用，并且不知道转发地址。
     */
    public static final int GONE = 410;

    /**
     * 有效负载太大：请求大于服务器愿意或能够处理的.
     */
    public static final int PAYLOAD_TOO_LARGE = 413;

    /**
     * 不支持的媒体类型：服务器不支持客户端请求的媒体类型。
     */
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;

    /**
     * 请求过多：用户在给定时间内发送了太多请求。
     */
    public static final int TOO_MANY_REQUESTS = 429;

    /**
     * 因法律原因不可用：由于法律原因，请求的资源不可用。
     */
    public static final int UNAVAILABLE_FOR_LEGAL_REASONS = 451;

    // 5xx (服务器错误)
    /**
     * 内部服务器错误：服务器遇到错误，无法完成请求。
     */
    public static final Integer INTERNAL_SERVER_ERROR = 500;

    /**
     * 未实现: 服务器不支持满足请求所需的功能。
     */
    public static final int NOT_IMPLEMENTED = 501;

    /**
     * 错误的网关: 服务器在充当网关或代理时，在尝试完成请求时从它访问的入站服务器收到无效响应。
     */
    public static final int BAD_GATEWAY = 502;

    /**
     * 服务不可用：由于服务器暂时过载或维护，服务器当前无法处理请求。
     */
    public static final int SERVICE_UNAVAILABLE = 503;

    /**
     * 网关超时：服务器在充当网关或代理时，没有从它需要访问的上游服务器及时收到响应以完成请求。
     */
    public static final int GATEWAY_TIMEOUT = 504;
}
