package com.ht.card.entities;


/**
 * 抢地主报文
 */
public class LandownerMessage {
    private String userid;
    private String roomid;
    private Integer seatid;
    private String  yesOrNo;


    public LandownerMessage(String userid, String roomid, Integer seatid, String yesOrNo) {
        this.userid = userid;
        this.roomid = roomid;
        this.seatid = seatid;
        this.yesOrNo = yesOrNo;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public Integer getSeatid() {
        return seatid;
    }

    public void setSeatid(Integer seatid) {
        this.seatid = seatid;
    }

    public String getYesOrNo() {
        return yesOrNo;
    }

    public void setYesOrNo(String yesOrNo) {
        this.yesOrNo = yesOrNo;
    }
}
