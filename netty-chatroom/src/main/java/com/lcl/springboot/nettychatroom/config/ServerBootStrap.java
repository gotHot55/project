package com.lcl.springboot.nettychatroom.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/9/2515:57
 */
@Component
public class ServerBootStrap {
    /**
     * 配置服务端的NIO线程组
     * NioEventLoopGroup 是用来处理I/O操作的Reactor线程组
     * bossGroup：用来接收进来的连接，workerGroup：用来处理已经被接收的连接,进行socketChannel的网络读写，
     * bossGroup接收到连接后就会把连接信息注册到workerGroup
     * workerGroup的EventLoopGroup默认的线程数是CPU核数的二倍
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address) {
        //ServerBootstrap 是一个启动NIO服务的辅助启动类
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)//设置group，将bossGroup， workerGroup线程组传递到ServerBootstrap
                //ServerSocketChannel是以NIO的selector为基础进行实现的，用来接收新的连接，这里告诉Channel通过NioServerSocketChannel获取新的连接
                .channel(NioServerSocketChannel.class)
                //设置 I/O处理类,主要用于网络I/O事件，记录日志，编码、解码消息
                .childHandler(new ServerInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                /**
                 * option是设置 bossGroup，childOption是设置workerGroup
                 * netty 默认数据包传输大小为1024字节, 设置它可以自动调整下一次缓冲区建立时分配的空间大小，避免内存的浪费    最小  初始化  最大 (根据生产环境实际情况来定)
                 * 使用对象池，重用缓冲区
                 */
                .childOption(ChannelOption.SO_KEEPALIVE, true);
                // 绑定端口，同步等待成功
        ChannelFuture future = bootstrap.bind(address).syncUninterruptibly();
        channel = future.channel();
        return future;
    }

    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        NettyConfig.group.close();
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
