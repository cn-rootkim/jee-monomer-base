package net.rootkim.baseservice.exception;

import lombok.extern.slf4j.Slf4j;
import net.rootkim.core.domain.vo.ResultVO;
import net.rootkim.core.domain.vo.ResultCode;
import net.rootkim.core.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.stream.Collectors;


/**
 * 全局捕捉异常
 *
 * @author RootKim[net.rootkim]
 * @since 2024/3/11
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleException(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException) {
            //参数校验失败
            Map<String, String> map = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage()));
            return new ResponseEntity(ResultVO.failed(ResultCode.FAILED, map), HttpStatus.OK);
        } else if (ex instanceof UnauthorizedException) {//未登录
            return new ResponseEntity(ResultVO.failed(ResultCode.UNAUTHORIZED), HttpStatus.OK);
        } else if (ex instanceof ForbiddenException) {//没有接口权限
            return new ResponseEntity(ResultVO.failed(ResultCode.FORBIDDEN), HttpStatus.OK);
        } else if (ex instanceof TokenExpiredException) {
            log.error("Token已过期异常", ex);
            return new ResponseEntity(ResultVO.failed(ex.getMessage()), HttpStatus.OK);
        } else if (ex instanceof LoginException) {
            log.error("登录异常", ex);
            return new ResponseEntity(ResultVO.failed(ex.getMessage()), HttpStatus.OK);
        } else if (ex instanceof ParamException) {
            log.error("参数异常", ex);
            return new ResponseEntity(ResultVO.failed(ex.getMessage()), HttpStatus.OK);
        } else if (ex instanceof SmsException) {
            log.error("短信异常", ex);
            return new ResponseEntity(ResultVO.failed(ex.getMessage()), HttpStatus.OK);
        } else if (ex instanceof BusinessException) {
            log.error("业务异常", ex);
            return new ResponseEntity(ResultVO.failed(ex.getMessage()), HttpStatus.OK);
        } else {
            log.error("系统异常", ex);
            return new ResponseEntity(ResultVO.failed("系统异常"), HttpStatus.OK);
        }
    }

}