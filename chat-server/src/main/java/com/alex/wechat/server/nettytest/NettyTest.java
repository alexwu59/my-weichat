package com.alex.wechat.server.nettytest;

import io.netty.channel.nio.NioEventLoopGroup;

public class NettyTest {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(5);

        group.next().execute(()-> System.out.println(Thread.currentThread().getName()));
        group.next().execute(()-> System.out.println(Thread.currentThread().getName()));
        group.next().execute(()-> System.out.println(Thread.currentThread().getName()));
        group.next().execute(()-> System.out.println(Thread.currentThread().getName()));
    }
}
