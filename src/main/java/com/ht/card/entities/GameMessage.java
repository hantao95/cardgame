package com.ht.card.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameMessage {
    private String userid;
    private String roomid;
    private Integer seatid;
    private List<Integer> cardList = new ArrayList<>();
    private String cards;

    public GameMessage(String userid, String roomid, Integer seatid, String cardList) {
        this.userid = userid;
        this.roomid = roomid;
        this.seatid = seatid;
        List<String> cardList0 = new ArrayList<String>(Arrays.asList(cardList.split("\\|")));
        this.cardList = cardList0.stream().map(a->Integer.parseInt(a)).collect(Collectors.toList());
        this.cards = cardList;

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

    public List<Integer> getCardList() {
        return cardList;
    }

    public void setCardList(List<Integer> cardList) {
        this.cardList = cardList;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }
}
