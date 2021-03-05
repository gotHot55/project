package com.lcl.springboot.nettychatroom.live;

import com.lcl.springboot.nettychatroom.httpserver.LiveDecode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2015:17
 */
public class LiveServer {
    private final Logger logger = LoggerFactory.getLogger(LiveServer.class);
    private int port;

    public LiveServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new LiveServer(8080).start();
    }

    private void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        logger.debug("initChannel -->" + ch);
                        ch.pipeline()
                                .addLast("decode", new LiveDecoder())
                                .addLast("encode", new LiveEncoder())
                                .addLast("aggregator", new HttpObjectAggregator(512 * 1024))
                                .addLast("handler", new LiveHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
        bootstrap.bind(port).sync();



    }
}
