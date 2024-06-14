package net.rootkim.baseservice.filter;

import cn.hutool.core.io.IoUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/28
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    private byte[] body = new byte[0];
    private boolean bufferFilled = false;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedServletInputStream(getBody());
    }

    public byte[] getBody() throws IOException {
        if (bufferFilled) {
            return Arrays.copyOf(body, body.length);
        }
        InputStream inputStream = super.getInputStream();
        body = IoUtil.readBytes(inputStream);
        bufferFilled = true;
        return body;
    }

    public class CachedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream buffer;

        public CachedServletInputStream(byte[] contents) {
            this.buffer = new ByteArrayInputStream(contents);
        }

        @Override
        public boolean isFinished() {
            return buffer.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public int read() throws IOException {
            return buffer.read();
        }
    }
}
