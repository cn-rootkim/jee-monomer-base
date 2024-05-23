package net.rootkim.core.utils;

import ch.qos.logback.classic.Level;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHttpRequest;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HttpClient5工具类
 *
 * @author RootKim[rootkim.net]
 * @since 2024/5/18
 */
@Slf4j
public class HttpClientUtil {

    private int maxAgain = 0;

    private RequestConfig config = null;

    CloseableHttpClient httpclient;

    public HttpClientUtil() {
        httpclient = HttpClients.createDefault();
    }

    public HttpClientUtil(int maxAgain, HttpHost proxy) {
        this.maxAgain = maxAgain;
        if (!ObjectUtils.isEmpty(proxy)) {
            config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
        }
        if (ObjectUtils.isEmpty(config)) {
            httpclient = HttpClients.createDefault();
        } else {
            httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        }
    }

    public String get(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        HttpEntity entity = this.execute(httpclient, httpGet, url, 0);
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }
        return EntityUtils.toString(entity);
    }

    public void getDownload(String url, File file) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        HttpEntity entity = this.execute(httpclient, httpGet, file.getName(), 0);
        if (ObjectUtils.isEmpty(entity)) {
            return;
        }
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                InputStream content = entity.getContent();
        ) {
            byte[] tmp = new byte[1024];
            int length;
            while ((length = content.read(tmp)) != -1) {
                fileOutputStream.write(tmp, 0, length);
            }
        }
    }

    private HttpEntity execute(CloseableHttpClient httpclient, HttpGet httpGet, String tip, int again) {
        try {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            return response.getEntity();
        } catch (Exception e) {
            if (again == maxAgain) {
                log.error(e.getMessage());
                log.error("try again all fail=" + tip);
                return null;
            } else {
                again++;
                log.warn(e.getMessage());
                log.warn("try " + again + " again=" + tip);
                return execute(httpclient, httpGet, tip, again);
            }
        }
    }
}
