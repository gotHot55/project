package com.lcl.springboot.nettychatroom.httpserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2011:08
 */
public class SSLChannelInitializer extends ChannelInitializer<SocketChannel> {
    private SslContext sslContext;

    public SSLChannelInitializer() {
        String keyStoreFIlePath = "D:/springcloudProject/lcl-microservice-cloud/netty-chatroom/src/main/java/com/lcl/springboot/nettychatroom/httpserver/javaboy.pkcs12";
        String keyStorePassword = "123456";
        try {
            KeyStore store = KeyStore.getInstance("PKCS12");
            store.load(new FileInputStream(keyStoreFIlePath), keyStorePassword.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(store,keyStorePassword.toCharArray());
            sslContext = SslContextBuilder.forServer(keyManagerFactory).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine sslEngine = sslContext.newEngine(ch.alloc());
        pipeline.addLast(new SslHandler(sslEngine))
                .addLast("decoder", new LiveDecode())
                .addLast("encoder", new HttpResponseEncoder())
                .addLast("aggregator", new HttpObjectAggregator(512 * 1024))
                .addLast("handler", new Handler());
    }
}
