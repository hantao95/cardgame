package com.ht.card.Dto;


import com.ht.card.Util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 存放底牌和玩家的手牌信息
 */
public class GameInfo {
    private List<Integer> play1= new ArrayList<>();
    private List<Integer> play2= new ArrayList<>();
    private List<Integer> play3= new ArrayList<>();
    private List<Integer> other= new ArrayList<>();//3张底牌

    private CardType cardType = CardType.CardType_Null;//当前牌型

    private int passtime = 0;//不要次数

    public GameInfo(){
        startGame();
    }

    //获取手牌
    public String getPlayList(int seatid){
        switch (seatid){
            case 0:return StringUtil.IntegerlistToString(play1);
            case 1:return StringUtil.IntegerlistToString(play2);
            case 2:return StringUtil.IntegerlistToString(play3);
        }
        return null;
    }
    //获取剩余手牌数量
    public String getCardNumber(){
        return play1.size()+"|"+play2.size()+"|"+play3.size();
    }

    //出牌
    public boolean putoutcard(int seatid ,List<Integer> cardList,CardType cardType){
        if(null==cardType){
            return false;
        }
        if((!this.cardType.morethan(cardType)||this.cardType.equals(CardType.CardType_Null))){
        }else{
            return false;
        }
            this.cardType = cardType;
        switch (seatid){
            case 0:play1.removeAll(cardList);break;
            case 1:play2.removeAll(cardList);break;
            case 2:play3.removeAll(cardList);break;
        }
        return true;
    }



    //底牌给地主
    public void landowner(int seatid){
        switch(seatid){
            case 0:play1.addAll(other);break;
            case 1:play2.addAll(other);break;
            case 2:play3.addAll(other);break;
        }
    }


    //游戏开始，随机发牌
    public void startGame(){
        int id=1;//扑克编号
        while (id<=54){
            int random = new Random().nextInt(4);
            if(0==random){
                if(other.size()<3){
                    other.add(id++);
                }
                continue;
            }else if(1==random){
                if(play1.size()<17){
                    play1.add(id++);
                }
                continue;
            }else if(2==random){
                if(play2.size()<17){
                    play2.add(id++);
                }
                continue;
            }else if(3==random){
                if(play3.size()<17){
                    play3.add(id++);
                }
                continue;
            }
        }
    }

    public void passtimeAdd(){
        passtime++;
    }

    public void passtimeClear(){
        passtime=0;
    }

    public boolean checkPass(){
        return passtime==2;
    }

    public int getPasstime() {
        return passtime;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public List<Integer> getPlay1() {
        return play1;
    }

    public void setPlay1(List<Integer> play1) {
        this.play1 = play1;
    }

    public List<Integer> getPlay2() {
        return play2;
    }

    public void setPlay2(List<Integer> play2) {
        this.play2 = play2;
    }

    public List<Integer> getPlay3() {
        return play3;
    }

    public void setPlay3(List<Integer> play3) {
        this.play3 = play3;
    }

    public List<Integer> getOther() {
        return other;
    }

    public void setOther(List<Integer> other) {
        this.other = other;
    }
}
