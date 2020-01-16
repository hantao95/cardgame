package com.ht.card.entities;

public class ChatMessage {
    private String roomid;
    private String userid;
    private String message;

    public  ChatMessage(String userid,String roomid,String message){
        this.message = message;
        this.userid = userid;
        this.roomid = roomid;
    }

    @Override
    public String toString() {
        return userid+":"+message;
    }

    public String getroomid() {
        return roomid;
    }

    public void setroomid(String roomid) {
        this.roomid = roomid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
