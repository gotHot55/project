package com.lcl.springboot.nettychatroom.httpserver;

import com.lcl.springboot.nettychatroom.config.ServerBootStrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/209:38
 */
public class HttpServer {
//    private final int port;
//
//
//    public HttpServer(int port) {
//        this.port = port;
//    }

    public static void main(String[] args) throws InterruptedException {
//        args = new String[]{"8080"};
//        if (args.length != 1) {
//            System.err.println( "Usage: " + HttpServer.class.getSimpleName() + " <port>");
//            return;
//        }
//       int port = Integer.parseInt(args[0]);
       new HttpServer().start();
    }

    private void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                /*.childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("socketChannel：--》" + socketChannel);
                        socketChannel.pipeline()
                                .addLast("decoder", new HttpRequestDecoder())
                                .addLast("encoder", new HttpResponseEncoder())
                                .addLast("aggregator", new HttpObjectAggregator(512 * 1024))
                                .addLast("handler", new Handler());
                    }
                })*/
                .childHandler(new SSLChannelInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
        bootstrap.bind(8080).sync();
    }
}
