package com.api.gateway;

import com.api.gateway.common.BaseHttpRequest;
import com.api.gateway.common.BaseHttpRequestVerify;
import com.api.gateway.constants.BaseConstant;
import com.api.gateway.monitor.BaseLogHandle;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;

/**
 * HttpRequestHandle
 *
 * @author zhangfuhao
 * @Desc request请求处理
 * @date 2018年10月18日
 * @Version V1.0
 */
public class HttpRequestHandle {
    /**
     * 基础日志处理
     */
    private static final BaseLogHandle BASE_LOG_HANDLE = new BaseLogHandle();

    /**
     * request 请求处理
     *
     * @param request HttpApiGetWayServerHandler.HttpRequest
     * @param content HttpApiGetWayServerHandler.ByteBuf
     * @return
     */
    public static String requsteHandle(HttpRequest request, ByteBuf content) {
        String result;
        /* 进行入参校验 */
        var verify = BaseHttpRequestVerify.verify(request);
        if (verify > 0) {
            /* 获取校验失败信息 */
            result = BaseHttpRequestVerify.getVerifyMessage(verify);
        } else {
            String body = BaseConstant.EMPTY_STRING;
            if (content.isReadable()) {
                body = content.toString(CharsetUtil.UTF_8);
                result = BaseHttpRequest.send(request.method(), request.uri(), body.getBytes());
            } else {
                result = BaseHttpRequest.send(request.method(), request.uri());
            }
            BASE_LOG_HANDLE.send(request.method().name(), request.uri(), body, result);
        }
        return result;
    }
}
