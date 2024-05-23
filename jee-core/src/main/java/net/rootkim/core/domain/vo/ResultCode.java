package net.rootkim.core.domain.vo;

import lombok.Getter;

/**
 * ResultCode枚举
 * @author RootKim[net.rootkim]
 * @since 2024/3/5
 */
@Getter
public enum ResultCode implements ErrorCode{
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    UNAUTHORIZED(401,"未登录"),
    TOKEN_EXPIRED(402,"token已过期"),
    FORBIDDEN(403,"没有权限");
    private int code;
    private String message;
    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
