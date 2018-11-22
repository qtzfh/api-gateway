package com.api.gateway.common;

import com.api.gateway.enums.BaseHttpMethodEnum;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;

/**
 * BaseHttpRequest
 *
 * @author zhangfuhao
 * @Desc 基础http请求方法
 * @date 2018年10月11日
 * @Version V1.0
 */
public class BaseHttpRequest {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(BaseHttpRequest.class);

    private static final HttpClient HTTP_CLIENT      = HttpClient.newHttpClient();
    /**
     * 默认超时时间（s）
     */
    private static final int        DEFAULT_TIME_OUT = 3;

    /**
     * 发送http请求
     *
     * @param httpMethod 请求方法
     * @param uri        请求地址
     * @return repsoneBody
     */
    public static String send(HttpMethod httpMethod, String uri) {
        return send(httpMethod, uri, null);
    }

    /**
     * 发送http请求
     *
     * @param httpMethod 请求方法
     * @param uri        请求地址
     * @return repsoneBody
     */
    public static String send(HttpMethod httpMethod, String uri, byte[] body) {
        HttpResponse<String> response = null;
        if (httpMethod.name().equals(BaseHttpMethodEnum.GET.name())) {
            response = BaseHttpRequest.get(uri);
        } else if (httpMethod.name().equals(BaseHttpMethodEnum.POST.name())) {
            response = BaseHttpRequest.post(uri, body);
        }
        return Objects.isNull(response) ? BaseHttpRequestVerify.defaultErrorMessage() : response.body();
    }

    /**
     * 发送get请求
     *
     * @param uri 请求地址
     * @return
     */
    private static HttpResponse<String> get(String uri) {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .timeout(Duration.ofSeconds(DEFAULT_TIME_OUT))
                    .GET()
                    .build();
            return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            LOGGER.error("HTTP GET请求错误", e);
        }
        return null;
    }

    /**
     * 发送post请求
     *
     * @param uri  请求地址
     * @param body 请求内容实体
     * @return
     */
    private static HttpResponse<String> post(String uri, byte[] body) {
        try {
            if (Objects.isNull(body)) {
                body = new byte[0];
            }
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .timeout(Duration.ofSeconds(DEFAULT_TIME_OUT))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                    .build();
            return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            LOGGER.error("HTTP POST请求错误", e);
        }
        return null;
    }
}
