package net.rootkim.baseservice.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/13
 */
public class RepeatableHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final int BUFFER_SIZE = 1024 * 8;
    private byte[] body;

    public RepeatableHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
}
