package com.lcl.springboot.nettychatroom.config;

import com.lcl.springboot.nettychatroom.handler.WebSocketHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/9/2515:53
 */
public class ServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("http-codec", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        pipeline.addLast("handler", new WebSocketHandler());
    }
}
