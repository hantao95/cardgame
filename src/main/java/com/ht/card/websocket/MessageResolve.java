package com.ht.card.websocket;

import com.ht.card.entities.ChatMessage;
import com.ht.card.entities.LandownerMessage;
import com.ht.card.entities.Message;
import com.ht.card.entities.ReadyMessage;

public class MessageResolve {
    //解析信息
    public static Message Resolve(String message){
        //type@roomid@message
        String[] info = message.split("@");
        return new Message(info[0],message.substring(message.indexOf("@")+1));
    }
    //解析聊天信息
    public static ChatMessage ResolveChat(String message){
        //userid@roomid@message
        String[] info = message.split("@");
        return new ChatMessage(info[0],info[1],info[2]);
    }

    //解析准备信息
    public static ReadyMessage ResolveReady(String message){
        //userid@roomid@seatid@status
        String[] info = message.split("@");
        return new ReadyMessage(info[0],info[1],Integer.parseInt(info[2]),info[3]);
    }

    //解析抢地主信息
    public static LandownerMessage ResolveLandowner(String message){
        //userid@roomid@seatid@status
        String[] info = message.split("@");
        return new LandownerMessage(info[0],info[1],Integer.parseInt(info[2]),info[3]);
    }
}

