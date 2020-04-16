package com.ht.card.entities;

import com.ht.card.Util.StringUtil;
import com.ht.card.websocket.GameLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * 抢地主信息
 */
public class LandownerInfo {
    //叫地主顺序
    private List<String> doLandowner= new ArrayList<>();
    private List<Integer> seatlist = new ArrayList<>();
    //地主
    private Integer landowner = -1;

    //更新叫地主信息,决定出地主
    public void doLandowner(LandownerMessage message){
        doLandowner.add(message.getYesOrNo());
        seatlist.add(message.getSeatid());
        landowner=GameLogic.checkLandowner(doLandowner,seatlist);
    }

    public String toString(){

        return landowner+"@"+StringUtil.StringlistToString(doLandowner)+"@"+StringUtil.IntegerlistToString(seatlist);
    }

    public Integer getLandowner() {
        return landowner;
    }

    public void setLandowner(Integer landowner) {
        this.landowner = landowner;
    }

    public List<String> getDoLandowner() {
        return doLandowner;
    }

    public void setDoLandowner(List<String> doLandowner) {
        this.doLandowner = doLandowner;
    }

    public List<Integer> getSeatlist() {
        return seatlist;
    }

    public void setSeatlist(List<Integer> seatlist) {
        this.seatlist = seatlist;
    }
}
