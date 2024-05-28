package net.rootkim.baseservice.filter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
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
        HttpServletRequest requestWrapper = null;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            requestWrapper = new RequestWrapper(request);
            StringBuilder stringBuilder = new StringBuilder("request start》》》\nip:");
            stringBuilder.append(IPUtil.getIpAddr(request));
            stringBuilder.append("\nurl：");
            stringBuilder.append(request.getRequestURI());
            stringBuilder.append("\nheader:{");
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                stringBuilder.append("\n  " + name + ":" + value);
            }
            stringBuilder.append("\n}");
            stringBuilder.append("\nparameter:{");
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                Object value = request.getParameter(name);
                stringBuilder.append("\n  " + name + ":" + value);
            }
            stringBuilder.append("\n}");
            stringBuilder.append("\nbody:");
            InputStream inputStream = requestWrapper.getInputStream();
            String body = IoUtil.read(inputStream, CharsetUtil.CHARSET_UTF_8);
            stringBuilder.append(body);
            stringBuilder.append("\nattribute:{");
            Enumeration<String> attributeNames = request.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String name = attributeNames.nextElement();
                Object value = request.getAttribute(name);
                stringBuilder.append("\n  " + name + ":" + value);
            }
            stringBuilder.append("\n}");
            log.info(stringBuilder.toString());
        } catch (Exception e) {
            log.error("全局请求日志打印异常:", e);
        } finally {
            ResponseWrapper responseWrapper = new ResponseWrapper(response);
            filterChain.doFilter(requestWrapper, responseWrapper);
            StringBuilder stringBuilder = new StringBuilder("response start》》》\nurl:");
            stringBuilder.append(request.getRequestURI());
            stringBuilder.append("\nheader:{");
            for (String headerName : responseWrapper.getHeaderNames()) {
                stringBuilder.append("\n  " + headerName + ":" + responseWrapper.getHeader(headerName));
            }
            stringBuilder.append("\n}");
            if (responseWrapper.getContentType().contains("json")) {
                stringBuilder.append("\nbody:");
                byte[] body = responseWrapper.getBody();
                stringBuilder.append(StrUtil.str(body, CharsetUtil.CHARSET_UTF_8));
            }
            log.debug(stringBuilder.toString());
        }
    }
}
