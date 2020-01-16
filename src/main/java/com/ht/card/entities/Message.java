package com.ht.card.entities;

public class Message {
    private String roomid;
    private String userid;
    private String message;

    public  Message(String userid,String roomid,String message){
        this.message = message;
        this.userid = userid;
        this.roomid = roomid;
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
