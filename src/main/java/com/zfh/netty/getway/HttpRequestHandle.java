package com.zfh.netty.getway;

import com.zfh.netty.common.BaseHttpRequest;
import com.zfh.netty.common.BaseHttpRequestVerify;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * HttpRequestHandle
 *
 * @author zhangfuhao
 * @Desc request请求处理
 * @date 2018年10月18日
 * @Version V1.0
 */
public class HttpRequestHandle {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(HttpRequestHandle.class);

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
            String uri = BaseHttpRequestVerify.getGateWayUri(request.uri());
            if (content.isReadable()) {
                result = BaseHttpRequest.send(request.method(), uri, content.toString(CharsetUtil.UTF_8).getBytes());
            } else {
                result = BaseHttpRequest.send(request.method(), uri);
            }
        }
        return result;
    }
}
