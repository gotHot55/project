package com.lcl.springboot.nettychatroom.live;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2015:17
 */
public class LiveHandler extends SimpleChannelInboundHandler<LiveMessage> {
    private Logger logger = LoggerFactory.getLogger(LiveHandler.class);
    HashMap<Integer, LiveChannelCache> channelCache = new HashMap<>();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LiveMessage msg) throws Exception {
        Channel channel = ctx.channel();
        final int hashCode = channel.hashCode();
        logger.debug("channel hashcode-->"+hashCode+"\nmessage-->"+msg+"\ncache-->"+channelCache.size());
        if (channelCache.containsKey(hashCode)) {
            logger.debug("channelCache.containsKey(hashCode), put key:" + hashCode);
            channel.closeFuture()
                    .addListener(future -> {
                        logger.debug("channel close, remove key:" + hashCode);
                        channelCache.remove(hashCode);
                    });
            ScheduledFuture scheduledFuture = ctx.executor().schedule(
                    () -> {
                        logger.debug("schedule runs, close channel:" + hashCode);
                        channel.close();
                    }, 10, TimeUnit.SECONDS
            );
            channelCache.put(hashCode, new LiveChannelCache(scheduledFuture, channel));
        }
        switch (msg.getType()) {
            case LiveMessage.TYPE_HEAD:
                LiveChannelCache cache = channelCache.get(hashCode);
                ScheduledFuture schedule = ctx.executor().schedule(
                        () -> {
                            channel.close();
                        }, 5, TimeUnit.SECONDS
                );
                cache.getScheduledFuture().cancel(true);
                cache.setScheduledFuture(schedule);
                ctx.channel().writeAndFlush(msg);
                break;
             case LiveMessage.TYPE_MESSAGE:
                channelCache.entrySet().stream().forEach(
                        entry->{
                            Channel otherChannel = entry.getValue().getChannel();
                            otherChannel.writeAndFlush(msg);
                        }
                );
                break;
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channelReadComplete-->执行了");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.debug("exceptionCaught--->执行了");
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
}
