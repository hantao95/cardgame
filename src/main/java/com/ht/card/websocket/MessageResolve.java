package com.ht.card.websocket;

import com.ht.card.entities.Message;

public class MessageResolve {
    //解析信息
    public static Message Resolve(String message){
        //userid@roomid@message
        String[] info = message.split("@");
        return new Message(info[0],info[1],info[2]);
    }
}
