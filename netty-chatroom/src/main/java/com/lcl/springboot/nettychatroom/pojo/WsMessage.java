package com.lcl.springboot.nettychatroom.pojo;

import lombok.Data;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/9/2515:48
 */
@Data
public class WsMessage {
    private int t;//消息类型
    private String n;//用户名称
    private long room_id;//房间id
    private String body;//消息主题
    private int err;//错误码

    public WsMessage(int t, String n, int err) {
        this.t = t;
        this.n = n;
        this.err = err;
    }
    public WsMessage(int t, String n) {
        this.t = t;
        this.n = n;
        this.err = 0;
    }

    public WsMessage(int t, String n, String body, int err) {
        this.t = t;
        this.n = n;
        this.body = body;
        this.err = err;
    }
    public WsMessage(int t, String n, String body) {
        this.t = t;
        this.n = n;
        this.body = body;
        this.err = 0;
    }
}
