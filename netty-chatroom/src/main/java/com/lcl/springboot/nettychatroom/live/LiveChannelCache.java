package com.lcl.springboot.nettychatroom.live;

import io.netty.channel.Channel;
import io.netty.util.concurrent.ScheduledFuture;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2015:18
 */
public class LiveChannelCache {
    private ScheduledFuture scheduledFuture;
    private Channel channel;

    public LiveChannelCache(ScheduledFuture scheduledFuture, Channel channel) {
        this.scheduledFuture = scheduledFuture;
        this.channel = channel;
    }

    public ScheduledFuture getScheduledFuture() {
        return scheduledFuture;
    }

    public void setScheduledFuture(ScheduledFuture scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
