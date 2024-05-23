package net.rootkim.core.exception;

/**
 * Token已过期异常
 * @author RootKim[net.rootkim]
 * @since 2024/3/13
 */
public class TokenExpiredException extends LoginException{
    public TokenExpiredException(String message) {
        super(message);
    }
}
