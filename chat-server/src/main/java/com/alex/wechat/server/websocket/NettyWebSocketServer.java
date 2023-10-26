package com.alex.wechat.server.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class NettyWebSocketServer {

    private static final int WEB_SOCKET_PORT = 9999;
    //负责处理链接
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workGroup = new NioEventLoopGroup(NettyRuntime.availableProcessors());


    private void run() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                //为bossGroup添加handler
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<NioSocketChannel>(){
                    @Override
                    protected void initChannel(NioSocketChannel sh) throws Exception {
                        sh.pipeline().addLast(new IdleStateHandler(30,0,0));
                        sh.pipeline().addLast(new HttpServerCodec());
                        sh.pipeline().addLast(new ChunkedWriteHandler());
                        sh.pipeline().addLast(new HttpObjectAggregator(8192));
                        sh.pipeline().addLast(new HttpHeadersHandler());
                        sh.pipeline().addLast(new WebSocketServerProtocolHandler("/"));
                        //sh.pipeline().addLast(NETTY_WEB_SOCKET_SERVER_HANDLER);
                    }
                });


        bootstrap.bind(WEB_SOCKET_PORT).sync();


    }
}
