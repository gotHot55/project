package com.lcl.springboot.nettychatroom.httpserver;

import lombok.Data;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2014:20
 */
@Data
public class LiveMessage {
    static final byte TYPE_HEAD = 1;
    static final byte TYPE_MESSAGE = 2;

    private byte type;
    private int length;
    private String content;

}
