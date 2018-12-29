package com.api.gateway.base;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * RequestInfo
 *
 * @author zhangfuhao
 * @Desc
 * @date 2018年12月29日
 * @Version V1.0
 */
@Getter
@Setter
@ToString
public class BaseRequestInfo {
    /**
     * 请求方法名称
     */
    private HttpMethod method;
    /**
     * 请求地址
     */
    private String uri;
    /**
     * 请求ip
     */
    private String clientIp;
    /**
     * 请求头
     */
    private HttpHeaders headers;
    /**
     * 请求内容
     */
    private ByteBuf content;
}
