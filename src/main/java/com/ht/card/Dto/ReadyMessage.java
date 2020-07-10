package com.ht.card.Dto;

/**
 * 准备信息，用于保存每个玩家的准备情况
 */
public class ReadyMessage {
    private String userid;
    private String roomid;
    private int seatid;
    private String status;

    public ReadyMessage(){
    }

    public ReadyMessage(String userid,String roomid,int seatid ,String status) {
        this.roomid = roomid;
        this.status = status;
        this.userid = userid;
        this.seatid = seatid;
    }

    @Override
    public String toString() {
        return userid+"@"+seatid+"@"+status;
    }

    public String getRoomid() {
        return roomid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSeatid() {
        return seatid;
    }

    public void setSeatid(int seatid) {
        this.seatid = seatid;
    }
}
