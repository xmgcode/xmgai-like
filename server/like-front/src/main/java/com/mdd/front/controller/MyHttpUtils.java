package com.mdd.front.controller;

import okhttp3.*;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Component
public class MyHttpUtils {

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public MyHttpUtils(ObjectMapper objectMapper) {
        this.client = new OkHttpClient.Builder().build();
        this.objectMapper = objectMapper;
    }

    /**
     * 发送POST请求，使用Map作为请求体参数
     *
     * @param url     请求URL
     * @param headers 请求头
     * @param bodyMap 请求体参数（Map格式）
     * @return 响应内容
     * @throws IOException
     */
    public String post(String url, Map<String, String> headers, Map<String, Object> bodyMap) throws IOException {



        // 将Map转换为JSON字符串
        String jsonBody = objectMapper.writeValueAsString(bodyMap);

        // 创建RequestBody
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(jsonBody, mediaType);

        // 构建请求
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body);

        // 添加请求头
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = requestBuilder.build();

        // 发送请求并返回响应
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }
}
