package com.lcl.springboot.nettychatroom.config;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.traffic.GlobalChannelTrafficCounter;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/9/2416:53
 */
public class NettyConfig {
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static String WS_HOST = "127.0.0.1";
    public static int WS_PORT = 9090;
}
