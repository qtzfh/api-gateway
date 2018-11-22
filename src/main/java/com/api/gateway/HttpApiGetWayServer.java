package com.api.gateway;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;


/**
 * HttpApiGetWayServer
 *
 * @author zhangfuhao
 * @Desc
 * @date 2018年06月27日
 * @Version V1.0
 */
public final class HttpApiGetWayServer {

    static final boolean SSL  = System.getProperty("ssl") != null;
    static final int     PORT = Integer.parseInt(System.getProperty("port", SSL ? "8443" : "8080"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }
        /* 用于接收Client端连接 */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        /* 用于实际的业务处理操作 */
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /* 进行server配置 */
            ServerBootstrap b = new ServerBootstrap();
            /* 设置tcp队列缓冲区 */
            b.option(ChannelOption.SO_BACKLOG, 1024)
                    /* 加入工作线程组 */
                    .group(bossGroup, workerGroup)
                    /* 指定通信类型 */
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    /* 绑定事件处理器 */
                    .childHandler(new HttpApiGetWayServerInitializer(sslCtx));
            /* 绑定端口号 */
            Channel ch = b.bind(PORT).sync().channel();
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
