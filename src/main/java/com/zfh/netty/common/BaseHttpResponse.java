package com.zfh.netty.common;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;


/**
 * BaseHttpResponse
 *
 * @author zhangfuhao
 * @Desc httpRepsonse返回值
 * @date 2018年10月11日
 * @Version V1.0
 */
public class BaseHttpResponse {

    private static final AsciiString CONTENT_TYPE_JSON = AsciiString.cached("application/json");

    /**
     * 100continue状态
     */
    public static FullHttpResponse httpContinueStatus() {
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
    }

    /**
     * 设置返回结果的response
     *
     * @param result    调用api返回的结果
     * @param keepAlive 是否需要保持连接
     * @return HttpResponse
     */
    public static FullHttpResponse getResponse(String result, boolean keepAlive) {
        if (StringUtils.isBlank(result)) {
            return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
        }
        var response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(result,
                CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, CONTENT_TYPE_JSON);
        if (keepAlive) {
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        return response;
    }
}
