package com.alex.wechat.server.websocket;

import cn.hutool.json.JSONUtil;
import com.alex.wechat.user.domain.req.WSBaseReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class NettyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>  {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        WSBaseReq wsBaseReq = JSONUtil.toBean(textWebSocketFrame.text(), WSBaseReq.class);

    }
}
