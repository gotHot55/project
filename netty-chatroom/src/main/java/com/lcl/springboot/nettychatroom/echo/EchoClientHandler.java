package com.lcl.springboot.nettychatroom.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * Todo
 *
 * @author Administrator
 * @date 2021/1/2017:40
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * 从服务器受到数据后调用此方法
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("echo client received message: " + byteBuf.toString(StandardCharsets.UTF_8));
    }
    /**
     * 客户端与服务器的连接建立后调用此方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello echo server", StandardCharsets.UTF_8));
//        super.channelActive(ctx);
    }
    /**
     * 捕获到异常时调用此方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
