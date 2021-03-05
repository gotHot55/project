package com.lcl.springboot.nettychatroom;

import com.lcl.springboot.nettychatroom.config.NettyConfig;
import com.lcl.springboot.nettychatroom.config.ServerBootStrap;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@SpringBootApplication
public class NettyChatroomApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(NettyChatroomApplication.class);

    @Autowired
    private ServerBootStrap ws;

    public static void main(String[] args) {
        SpringApplication.run(NettyChatroomApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Netty's server is listen: " + NettyConfig.WS_PORT);
        InetSocketAddress address = new InetSocketAddress(NettyConfig.WS_HOST, NettyConfig.WS_PORT);
        ChannelFuture future = ws.start(address);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                ws.destroy();
            }
        });

        future.channel().closeFuture().syncUninterruptibly();
    }
}
