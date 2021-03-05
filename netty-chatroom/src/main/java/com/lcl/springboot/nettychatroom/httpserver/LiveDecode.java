package com.lcl.springboot.nettychatroom.httpserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2013:56
 */
public class LiveDecode extends ReplayingDecoder<LiveDecode.LiveState> {

    public enum LiveState {
        LENGTH,
        CONTENT;
    }

    private LiveMessage message = new LiveMessage();

    public LiveDecode() {
        super(LiveState.LENGTH);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (state()) {
            case LENGTH:
                int length = byteBuf.readInt();
                if (length > 0) {
                    checkpoint(LiveState.CONTENT);
                }else {
                    list.add(message);
                }
                break;
            case CONTENT:
                byte[] bytes = new byte[message.getLength()];
                byteBuf.readBytes(bytes);
                String content = new String(bytes);
                message.setContent(content);
                list.add(message);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + state());
        }
    }
}
