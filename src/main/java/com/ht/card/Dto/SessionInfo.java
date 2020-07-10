package com.ht.card.Dto;

import javax.websocket.Session;
import java.util.Objects;

/**
 * 存放session数据
 */
public class SessionInfo  {
    //用户
    private String userid;
    //房间
    private String roomid;
    //座位号
    private int seatid;
    //websocket连接
    private Session session;

    public SessionInfo(String userid,String roomid,int seatid,Session session){
        this.userid = userid;
        this.roomid = roomid;
        this.seatid = seatid;
        this.session = session;
    }

    public SessionInfo(String userid,Session session){
        this.userid = userid;
        this.roomid = "0";
        this.session = session;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getSeatid() {
        return seatid;
    }

    public void setSeatid(int seatid) {
        this.seatid = seatid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionInfo that = (SessionInfo) o;
        return Objects.equals(userid, that.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid);
    }
}
