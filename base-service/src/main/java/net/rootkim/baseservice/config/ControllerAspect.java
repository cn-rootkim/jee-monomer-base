package net.rootkim.baseservice.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.rootkim.core.utils.IPUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/13
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Before("execution(* net.rootkim.baseservice.controller..*.*(..))")
    public void controllerBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        try {
            log.debug("request start》》》\nIP:{}\nURL：{}\nheader:{}\nparam:{}", IPUtil.getIpAddr(request),
                    request.getRequestURL().toString(),
                    JSONObject.toJSONString(this.getHeader(request)), JSONObject.toJSONString(this.getRequestParams(joinPoint)));
        } catch (Exception e) {
            log.error("全局请求日志打印异常:", e);
        }
    }


    @AfterReturning(value = "execution(* net.rootkim.baseservice.controller..*.*(..))", returning = "result")
    public void controllerAfter(Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletResponse response = attributes.getResponse();
        Map<String, Object> headers = this.getHeader(response);
        String resultStr;
        if (headers.get("Content-Type") != null && headers.get("Content-Type").toString().contains("application/octet-stream;")) {
            resultStr = "file download," + headers.get("Content-Disposition");
        } else {
            try{
                resultStr = JSONObject.toJSONString(result);
            } catch (Exception e){
                resultStr = result.toString();
            }
        }
        log.debug("response end》》》\nheader:{}\nresult:{}", headers, resultStr);
    }

    /**
     * 获取入参
     *
     * @param joinPoint
     * @return
     */
    private Map<String, Object> getRequestParams(JoinPoint joinPoint) {
        Map<String, Object> requestParams = new HashMap<>();
        //参数名
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        List<String> paramNameList = new ArrayList<>();
        //参数值
        Object[] paramValues = joinPoint.getArgs();
        List<Object> paramValuesList = new ArrayList<>();
        for (int i = 0; i < paramValues.length; i++) {
            if (paramValues[i] instanceof HttpServletRequest
                    || paramValues[i] instanceof HttpServletResponse) {
                continue;
            }
            paramNameList.add(paramNames[i]);
            paramValuesList.add(paramValues[i]);
        }
        paramNames = paramNameList.toArray(new String[0]);
        paramValues = paramValuesList.toArray();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];
            //如果是文件对象
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                value = file.getOriginalFilename();
            }
            requestParams.put(paramNames[i], value);
        }
        return requestParams;
    }

    private Map<String, Object> getHeader(HttpServletRequest request) {
        Map<String, Object> headerParams = new HashMap<>(1);
        // 获取所有请求头名称并遍历
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headerParams.put(headerName, headerValue);
        }
        return headerParams;
    }

    private Map<String, Object> getHeader(HttpServletResponse response) {
        Map<String, Object> headerParams = new HashMap<>(1);
        // 获取所有请求头名称并遍历
        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName : headerNames) {
            String headerValue = response.getHeader(headerName);
            headerParams.put(headerName, headerValue);
        }
        return headerParams;
    }

}
