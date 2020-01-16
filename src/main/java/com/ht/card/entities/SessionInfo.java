package com.ht.card.entities;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Objects;

/**
 * 存放session数据
 */
public class SessionInfo  {
    private String userid;
    private String roomid;
    private Session session;

    public SessionInfo(String userid,String roomid,Session session){
        this.userid = userid;
        this.roomid = roomid;
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
