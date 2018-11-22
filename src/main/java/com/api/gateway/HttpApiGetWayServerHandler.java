package com.api.gateway;

import com.api.gateway.common.BaseHttpResponse;
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

/**
 * HttpApiGetWayServerHandler
 *
 * @author zhangfuhao
 * @Desc http请求处理类
 * @date 2018年06月27日
 * @Version V1.0
 */
public class HttpApiGetWayServerHandler extends SimpleChannelInboundHandler<Object> {
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
                ctx.write(BaseHttpResponse.httpContinueStatus());
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
                    ctx.write(BaseHttpResponse.getResponse(HttpRequestHandle.requsteHandle(this.request, content),
                            keepAlive));
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
}
