package com.lcl.springboot.nettychatroom.live;

import lombok.Data;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2015:16
 */
@Data
public class LiveMessage {
    static final byte TYPE_HEAD = 1;
    static final byte TYPE_MESSAGE = 2;

    private byte type;
    private int length;
    private String content;

}
