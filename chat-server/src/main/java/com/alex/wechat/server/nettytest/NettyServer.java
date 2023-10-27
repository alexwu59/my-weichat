package com.alex.wechat.server.nettytest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {


    EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
    public void run() throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossLoopGroup,workerLoopGroup)
                .channel(NioServerSocketChannel.class)
                //.option(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new NettyDiscardHandler());
                    }
                });

        ChannelFuture sync = bootstrap.bind(9999).sync();

    }

    public static void main(String[] args) throws Exception {
        NettyServer server = new NettyServer();
        server.run();
    }
}
