package net.rootkim.baseservice.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/28
 */
public class CachedServletInputStream extends ServletInputStream {
    private ByteArrayInputStream buffer;

    public CachedServletInputStream(byte[] contents) {
        this.buffer = new ByteArrayInputStream(contents);
    }

    @Override
    public int read() throws IOException {
        return buffer.read();
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
    public void setReadListener(ReadListener listener) {
        throw new RuntimeException("Not implemented");
    }
}
