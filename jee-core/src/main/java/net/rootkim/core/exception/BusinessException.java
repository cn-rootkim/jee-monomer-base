package net.rootkim.core.exception;

/**
 * 业务异常
 * @author RootKim[net.rootkim]
 * @since 2024/3/12
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
