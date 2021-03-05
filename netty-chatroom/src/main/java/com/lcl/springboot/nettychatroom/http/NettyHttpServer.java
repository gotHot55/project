package com.lcl.springboot.nettychatroom.http;

import com.lcl.springboot.nettychatroom.config.ServerBootStrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/1715:52
 */
public class NettyHttpServer implements Runnable {
    private Logger logger = LoggerFactory.getLogger(NettyHttpServer.class);
    private int port;
    private DispatcherServlet servlet;

    public NettyHttpServer(int port) {
        this.port = port;
    }

    public NettyHttpServer(int port, DispatcherServlet servlet) {
        this.port = port;
        this.servlet = servlet;
    }

    public void start() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpRequestHandler(servlet))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            logger.info("nettyhttpserver run successfully--->>");
            try {
                ChannelFuture f = b.bind(port).sync();
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    @Override
    public void run() {
        start();
    }
}
