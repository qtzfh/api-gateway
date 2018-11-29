package com.api.gateway.handle;

import com.api.gateway.base.BaseHttpRequest;
import com.api.gateway.monitor.BaseLogHandle;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;

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
    public static String requestHandle(HttpRequest request, ByteBuf content) {
        String result;
        /* 进行入参校验 */
        var verify = ApiVerifyHandle.verify(request);
        if (verify > 0) {
            /* 获取校验失败信息 */
            result = ApiVerifyHandle.getVerifyMessage(verify);
        } else {
            String body = StringUtils.EMPTY;
            String uri = GateWayConfig.getGateWayUrl(request);
            if (content.isReadable()) {
                body = content.toString(CharsetUtil.UTF_8);
                result = BaseHttpRequest.send(request.method(), uri, body);
            } else {
                result = BaseHttpRequest.send(request.method(), uri);
            }
            BASE_LOG_HANDLE.send(request.method().name(), request.uri(), body, result);
        }
        return result;
    }
}
