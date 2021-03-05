package com.lcl.springboot.nettychatroom.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * Todo
 *
 * @author Administrator
 * @date 2021/1/2017:58
 */
public class EchoServiceHandler extends ChannelInboundHandlerAdapter {
    /**
     * 每个信息入栈都会调用此方法处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        super.channelRead(ctx, msg);
        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println("echo server received message : " + byteBuf.readBytes(byteBuf.readableBytes()).toString(StandardCharsets.UTF_8));
        byteBuf.discardReadBytes();
        System.out.println("go on");

        ChannelFuture future = ctx.writeAndFlush(byteBuf);
        future.addListener( futureListener ->
        {
            if (futureListener.isSuccess())
            {
                System.out.println("echo server write message success");
            }
            else
            {
                System.out.println("echo server write message failed");
            }
        });
    }
    /**
     *   当前批处理的消息中最后一条消息时会被调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client --- channelReadComplete" , StandardCharsets.UTF_8));
//        super.channelReadComplete(ctx);
    }
    /**
     * 读操作捕获到异常时会被调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
