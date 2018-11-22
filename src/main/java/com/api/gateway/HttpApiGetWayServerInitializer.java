package com.api.gateway;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.ssl.SslContext;

/**
 * HttpApiGetWayServerInitializer
 *
 * @author zhangfuhao
 * @Desc
 * @date 2018年06月27日
 * @Version V1.0
 */
public class HttpApiGetWayServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * request请求最大的content长度
     */
    private static final int MAX_CONTENT_LENGTH = 1024 * 1024 * 1;

    private final SslContext sslCtx;

    public HttpApiGetWayServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpApiGetWayServerHandler());
        p.addLast(new HttpObjectAggregator(MAX_CONTENT_LENGTH));
    }
}
