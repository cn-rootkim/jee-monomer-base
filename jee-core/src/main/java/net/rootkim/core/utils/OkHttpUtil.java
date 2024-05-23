package net.rootkim.core.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpUtil {

    private static OkHttpClient createOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)//连接超时时间
                .readTimeout(20, TimeUnit.SECONDS)//读超时时间
                .writeTimeout(60, TimeUnit.SECONDS)//写超时时间
                .build();
        return okHttpClient;
    }

    public static String sendGet(String url) {
        log.info("OkHttp_get请求url:{}", url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        String result = null;
        try (Response response = createOkHttpClient().newCall(request).execute()) {
            if (ObjectUtils.isEmpty(response)) {
                throw new RuntimeException("请求失败,response为空");
            }
            if (response.code() != 200) {
                throw new RuntimeException("请求失败,状态码:" + response.code());
            }
            if (ObjectUtils.isEmpty(response.body())) {
                throw new RuntimeException("请求失败,response.body()为空");
            }
            result = response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String sendPostJson(String url, JSONObject requestBody) {
        if(ObjectUtils.isEmpty(requestBody)){
            log.info("OkHttp请求url:{}", url);
            requestBody = new JSONObject();
        }else{
            log.info("OkHttp_postJson请求url:{}，请求requestBody:{}", url, requestBody.toJSONString());
        }
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, requestBody.toJSONString()))
                .build();
        String result = null;
        try (Response response = createOkHttpClient().newCall(request).execute()) {
            if (ObjectUtils.isEmpty(response)) {
                throw new RuntimeException("请求失败,response为空");
            }
            if (response.code() != 200) {
                throw new RuntimeException("请求失败,状态码:" + response.code());
            }
            if (ObjectUtils.isEmpty(response.body())) {
                throw new RuntimeException("请求失败,response.body()为空");
            }
            result = response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}