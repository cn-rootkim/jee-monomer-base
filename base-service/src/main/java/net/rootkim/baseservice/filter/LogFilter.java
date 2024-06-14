package net.rootkim.baseservice.filter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.rootkim.core.utils.IPUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/28
 */
@Component
@Order(2)
@Slf4j
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            RequestWrapper requestWrapper = new RequestWrapper(request);
            JSONObject header = new JSONObject();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                header.put(name, value);
            }
            JSONObject parameter = new JSONObject();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                Object value = request.getParameter(name);
                parameter.put(name, value);
            }
            JSONObject attribute = new JSONObject();
            Enumeration<String> attributeNames = request.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String name = attributeNames.nextElement();
                Object value = request.getAttribute(name);
                attribute.put(name, value);
            }
            String body = StrUtil.str(requestWrapper.getBody(), CharsetUtil.CHARSET_UTF_8);
            log.info(StrUtil.format("request start》》》\nip:{}\nurl：{}\nheader:{}\nparameter:{}\nattribute:{}\nbody:{}",
                    IPUtil.getIpAddr(request), request.getRequestURI(), header.toJSONString(), parameter.toJSONString(), attribute.toJSONString(), body));

            HttpServletResponse response = (HttpServletResponse) servletResponse;
            ResponseWrapper responseWrapper = new ResponseWrapper(response);
            filterChain.doFilter(requestWrapper, responseWrapper);
            header = new JSONObject();
            for (String headerName : responseWrapper.getHeaderNames()) {
                header.put(headerName, responseWrapper.getHeader(headerName));
            }
            body = null;
            if (responseWrapper.getContentType().contains("json")) {
                byte[] bodyByte = responseWrapper.getBody();
                body = StrUtil.str(bodyByte, CharsetUtil.CHARSET_UTF_8);
            }
            log.debug(StrUtil.format("response start》》》\nurl:{}\nheader:{}\nbody:{}", request.getRequestURI(), header, body));
        } catch (Exception e) {
            log.error("全局请求日志打印异常:", e);
        }
    }
}
