package net.rootkim.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 返回结果类
 * @author RootKim[net.rootkim]
 * @since 2024-2-16
 */
@AllArgsConstructor
@Getter
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 1108029660090500118L;

    public interface ResultView{};

    @JsonView({ResultView.class})
    private int code;

    @JsonView({ResultView.class})
    private String message;

    @JsonView({ResultView.class})
    private T data;

    public static<T> ResultVO<T> success(){
        return new ResultVO<>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),null);
    }

    public static<T> ResultVO<T> success(String message){
        return new ResultVO<>(ResultCode.SUCCESS.getCode(),message,null);
    }

    public static<T> ResultVO<T> success(T data){
        return new ResultVO<>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),data);
    }

    public static<T> ResultVO<T> success(String message, T data){
        return new ResultVO<>(ResultCode.SUCCESS.getCode(),message,data);
    }

    public static<T> ResultVO<T> failed(){
        return new ResultVO<>(ResultCode.FAILED.getCode(),ResultCode.FAILED.getMessage(),null);
    }

    public static<T> ResultVO<T> failed(String message){
        return new ResultVO<>(ResultCode.FAILED.getCode(),message,null);
    }

    public static<T> ResultVO<T> failed(String message, T data){
        return new ResultVO<>(ResultCode.FAILED.getCode(),message,data);
    }

    public static<T> ResultVO<T> failed(ErrorCode errorCode){
        return new ResultVO<>(errorCode.getCode(),errorCode.getMessage(),null);
    }

    public static<T> ResultVO<T> failed(ErrorCode errorCode, T data){
        return new ResultVO<>(errorCode.getCode(),errorCode.getMessage(),data);
    }
}
