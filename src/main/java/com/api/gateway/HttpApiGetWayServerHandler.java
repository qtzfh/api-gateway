package com.api.gateway;

import com.api.gateway.base.BaseRequestInfo;
import com.api.gateway.handle.HttpRequestHandle;
import com.api.gateway.handle.HttpResponseHandle;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * HttpApiGetWayServerHandler
 *
 * @author zhangfuhao
 * @Desc http请求处理类
 * @date 2018年06月27日
 * @Version V1.0
 */
public class HttpApiGetWayServerHandler extends SimpleChannelInboundHandler<Object> {
    /**
     * 获取ip请求头
     */
    private static final String IP_HEADER = "X-Forwarded-For";

    private volatile HttpRequest request;

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            HttpRequest request = this.request = (HttpRequest) msg;
            if (HttpUtil.is100ContinueExpected(request)) {
                ctx.write(HttpResponseHandle.httpContinueStatus());
            }
            if (request.decoderResult().isSuccess()) {
                return;
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf content = httpContent.content();
            if (msg instanceof LastHttpContent) {
                boolean keepAlive = HttpUtil.isKeepAlive(this.request);
                if (keepAlive) {
                    BaseRequestInfo baseRequestInfo = convertBaseRequest(this.request, content, getClientIp(ctx));
                    String result = HttpRequestHandle.requestHandle(baseRequestInfo);
                    ctx.write(HttpResponseHandle.getResponse(result, keepAlive));
                } else {
                    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        /* 可以忽略的io异常 */
        boolean ignoreException = ctx.channel().isActive() && cause instanceof IOException;
        if (!ignoreException) {
            cause.printStackTrace();
        }
        ctx.close();
    }

    /**
     * request请求封装成BaseRequestInfo
     *
     * @param httpRequest netty httprequest
     * @param content     请求内容
     * @param clientIp    请求ip
     * @return BaseRequestInfo
     */
    private BaseRequestInfo convertBaseRequest(HttpRequest httpRequest, ByteBuf content, String clientIp) {
        BaseRequestInfo baseRequestInfo = new BaseRequestInfo();
        baseRequestInfo.setClientIp(clientIp);
        baseRequestInfo.setContent(content);
        baseRequestInfo.setHeaders(httpRequest.headers());
        baseRequestInfo.setMethod(httpRequest.method());
        baseRequestInfo.setUri(httpRequest.uri());
        return baseRequestInfo;
    }

    /**
     * 获取请求ip
     *
     * @param ctx
     * @return ip地址
     */
    private String getClientIp(ChannelHandlerContext ctx) {
        String clientIP = request.headers().get(IP_HEADER);
        if (clientIP == null) {
            InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
            clientIP = insocket.getAddress().getHostAddress();
        }
        return clientIP;
    }
}
