package com.ht.card.init;

import com.ht.card.Constant.CardConstant;
import com.ht.card.Dto.Card;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    public static Map<Integer,Card> cardMap = new HashMap<>();
    //初始化卡牌信息
    public static void initCard(){
        cardMap.put(1,new Card(1,"3",CardConstant.cardType_Spades));
        cardMap.put(2,new Card(2,"3",CardConstant.cardType_Heart));
        cardMap.put(3,new Card(3,"3",CardConstant.cardType_Flower));
        cardMap.put(4,new Card(4,"3",CardConstant.cardType_Orthogon));
        cardMap.put(5,new Card(5,"4",CardConstant.cardType_Spades));
        cardMap.put(6,new Card(6,"4",CardConstant.cardType_Heart));
        cardMap.put(7,new Card(7,"4",CardConstant.cardType_Flower));
        cardMap.put(8,new Card(8,"4",CardConstant.cardType_Orthogon));
        cardMap.put(9,new Card(9,"5",CardConstant.cardType_Spades));
        cardMap.put(10,new Card(10,"5",CardConstant.cardType_Heart));
        cardMap.put(11,new Card(11,"5",CardConstant.cardType_Flower));
        cardMap.put(12,new Card(12,"5",CardConstant.cardType_Orthogon));
        cardMap.put(13,new Card(13,"6",CardConstant.cardType_Spades));
        cardMap.put(14,new Card(14,"6",CardConstant.cardType_Heart));
        cardMap.put(15,new Card(15,"6",CardConstant.cardType_Flower));
        cardMap.put(16,new Card(16,"6",CardConstant.cardType_Orthogon));
        cardMap.put(17,new Card(17,"7",CardConstant.cardType_Spades));
        cardMap.put(18,new Card(18,"7",CardConstant.cardType_Heart));
        cardMap.put(19,new Card(19,"7",CardConstant.cardType_Flower));
        cardMap.put(20,new Card(20,"7",CardConstant.cardType_Orthogon));
        cardMap.put(21,new Card(21,"8",CardConstant.cardType_Spades));
        cardMap.put(22,new Card(22,"8",CardConstant.cardType_Heart));
        cardMap.put(23,new Card(23,"8",CardConstant.cardType_Flower));
        cardMap.put(24,new Card(24,"8",CardConstant.cardType_Orthogon));
        cardMap.put(25,new Card(25,"9",CardConstant.cardType_Spades));
        cardMap.put(26,new Card(26,"9",CardConstant.cardType_Heart));
        cardMap.put(27,new Card(27,"9",CardConstant.cardType_Flower));
        cardMap.put(28,new Card(28,"9",CardConstant.cardType_Orthogon));
        cardMap.put(29,new Card(29,"10",CardConstant.cardType_Spades));
        cardMap.put(30,new Card(30,"10",CardConstant.cardType_Heart));
        cardMap.put(31,new Card(31,"10",CardConstant.cardType_Flower));
        cardMap.put(32,new Card(32,"10",CardConstant.cardType_Orthogon));
        cardMap.put(33,new Card(33,"J",CardConstant.cardType_Spades));
        cardMap.put(34,new Card(34,"J",CardConstant.cardType_Heart));
        cardMap.put(35,new Card(35,"J",CardConstant.cardType_Flower));
        cardMap.put(36,new Card(36,"J",CardConstant.cardType_Orthogon));
        cardMap.put(37,new Card(37,"Q",CardConstant.cardType_Spades));
        cardMap.put(38,new Card(38,"Q",CardConstant.cardType_Heart));
        cardMap.put(39,new Card(39,"Q",CardConstant.cardType_Flower));
        cardMap.put(40,new Card(40,"Q",CardConstant.cardType_Orthogon));
        cardMap.put(41,new Card(41,"K",CardConstant.cardType_Spades));
        cardMap.put(42,new Card(42,"K",CardConstant.cardType_Heart));
        cardMap.put(43,new Card(43,"K",CardConstant.cardType_Flower));
        cardMap.put(44,new Card(44,"K",CardConstant.cardType_Orthogon));
        cardMap.put(45,new Card(45,"A",CardConstant.cardType_Spades));
        cardMap.put(46,new Card(46,"A",CardConstant.cardType_Heart));
        cardMap.put(47,new Card(47,"A",CardConstant.cardType_Flower));
        cardMap.put(48,new Card(48,"A",CardConstant.cardType_Orthogon));
        cardMap.put(49,new Card(49,"2",CardConstant.cardType_Spades));
        cardMap.put(50,new Card(50,"2",CardConstant.cardType_Heart));
        cardMap.put(51,new Card(51,"2",CardConstant.cardType_Flower));
        cardMap.put(52,new Card(52,"2",CardConstant.cardType_Orthogon));
        cardMap.put(53,new Card(53,"SKing",CardConstant.cardType_King));
        cardMap.put(54,new Card(54,"BKing",CardConstant.cardType_King));
    }

}
