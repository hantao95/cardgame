package com.ht.card.websocket;

import com.ht.card.Constant.CardConstant;
import com.ht.card.Util.StringUtil;
import com.ht.card.entities.Card;
import com.ht.card.entities.CardType;
import com.ht.card.init.Cache;

import java.util.*;
import java.util.stream.Collectors;

public class GameLogic {

    //决定地主
    public static Integer checkLandowner(List<String> doLandowner,List<Integer> seatlist){
        String sDoLandowner = StringUtil.StringlistToString(doLandowner);
        Integer landowner = -1;
        if(doLandowner.size()==3){
            switch(sDoLandowner){
                case "YNN":landowner = seatlist.get(0);break;
                case "NYN":landowner = seatlist.get(1);break;
                case "NNY":landowner = seatlist.get(2);break;
                case "NNN":landowner = -10000;break;
            }
        }else if(doLandowner.size()==4){
            switch(sDoLandowner){
                case "YYNN":landowner = seatlist.get(1);break;
                case "YYYN":landowner = seatlist.get(2);break;
                case "YYNY":landowner = seatlist.get(3);break;
                case "YYYY":landowner = seatlist.get(3);break;
                case "YNYN":landowner = seatlist.get(2);break;
                case "YNYY":landowner = seatlist.get(3);break;
            }
        }else if(doLandowner.size()==5){
            switch(sDoLandowner){
                case "NYYNY":landowner = seatlist.get(4);break;
                case "NYYNN":landowner = seatlist.get(4);break;
            }
        }
        return landowner;
    }

    //判断牌型
    public static CardType checkcard(List<Integer> cardList){
        Integer sortno = -1;
        if(isPass(cardList)!=-1)
            return CardType.CardType_Null;
        if((sortno=isBoom(cardList))!=-1)
            return new CardType(CardConstant.type_Boom,sortno);
        if((sortno=isSingle(cardList))!=-1)
            return new CardType(CardConstant.type_Single,sortno);
        if((sortno=isPair(cardList))!=-1)
            return new CardType(CardConstant.type_Pair,sortno);
        if((sortno=isThree(cardList))!=-1)
            return new CardType(CardConstant.type_Three,sortno);
        if((sortno=isThreePair(cardList))!=-1)
            return new CardType(CardConstant.type_ThreePair,sortno);
        if((sortno=isStraight(cardList))!=-1)
            return new CardType(CardConstant.type_Straight,sortno);
        if((sortno=isStraightPair(cardList))!=-1)
            return new CardType(CardConstant.type_StraightPair,sortno);
        if((sortno=isPlane(cardList))!=-1)
            return new CardType(CardConstant.type_Plane,sortno);
        if((sortno=isPlanePair(cardList))!=-1)
            return new CardType(CardConstant.type_PlanePair,sortno);
        return null;
    }

    //是否是不要
    public static Integer isPass(List<Integer> cardList){
        if(cardList.size()==1&&cardList.get(0)==-10000){
                return 1;
        }
        return -1;
    }

    //是否是炸弹
    public static Integer isBoom(List<Integer> cardList){
        if(cardList.size()==4&&getCardInfo(cardList.get(0)).getName()==getCardInfo(cardList.get(1)).getName()
                &&getCardInfo(cardList.get(0)).getName()==getCardInfo(cardList.get(2)).getName()
                &&getCardInfo(cardList.get(0)).getName()==getCardInfo(cardList.get(3)).getName()){
            return cardList.get(0);
        }else if(cardList.size()==2&&getCardInfo(cardList.get(0)).getName()=="BKing"&&getCardInfo(cardList.get(1)).getName()=="SKing"){
            return cardList.get(0);
        }
        return -1;
    }

    //是否是单张
    public static Integer isSingle(List<Integer> cardList){
        if(cardList.size()==1) return cardList.get(0);
        return -1;
    }

    //是否是对子
    public  static Integer isPair(List<Integer> cardList){
        if(cardList.size()==2&&getCardInfo(cardList.get(0)).getName()==getCardInfo(cardList.get(1)).getName())
            return  cardList.get(0);
        return -1;
    }

    //是否是三张

    public static Integer isThree(List<Integer> cardList){
        if(cardList.size()==2&&getCardInfo(cardList.get(0)).getName()==getCardInfo(cardList.get(1)).getName()
                &&getCardInfo(cardList.get(0)).getName()==getCardInfo(cardList.get(2)).getName())
            return  cardList.get(0);
        return -1;
    }

    //是否是三带二
    public static Integer isThreePair(List<Integer> cardList){
        if(cardList.size()==5){
            // 3-2
            if(getCardInfo(cardList.get(0)).getName()==getCardInfo(cardList.get(1)).getName()
                    &&getCardInfo(cardList.get(0)).getName()==getCardInfo(cardList.get(2)).getName()
                    &&getCardInfo(cardList.get(3)).getName()==getCardInfo(cardList.get(4)).getName()){
                return cardList.get(0);
            }
            //2-3
            if(getCardInfo(cardList.get(0)).getName()==getCardInfo(cardList.get(1)).getName()
                    &&getCardInfo(cardList.get(2)).getName()==getCardInfo(cardList.get(3)).getName()
                    &&getCardInfo(cardList.get(2)).getName()==getCardInfo(cardList.get(4)).getName()){
                return cardList.get(2);
            }
        }
        return -1;
    }

    //是否是顺子
    public static Integer isStraight(List<Integer> cardList){
        if(cardList.size()>4&&cardList.size()<13){
            String str = "AKQJ109876543";
            String card =cardList.stream().map(a->getCardInfo(a).getName()).collect(Collectors.joining()
            );
            if(str.contains(card)){
                return cardList.get(0);
            }
        }
        return -1;
    }

    //是否连对
    public static Integer isStraightPair(List<Integer> cardList){
        if(cardList.size()>5&&cardList.size()<21){
            String str = "AAKKQQJJ101099887766554433";
            String card =cardList.stream().map(a->getCardInfo(a).getName()).collect(Collectors.joining()
            );
            if(str.contains(card)){
                return cardList.get(0);
            }
        }
        return -1;
    }

    //是否飞机
    public static Integer isPlane(List<Integer> cardList){
        if(cardList.size()>5&&cardList.size()<19){
            String str = "AAAKKKQQQJJJ101010999888777666555444333";
            String card =cardList.stream().map(a->getCardInfo(a).getName()).collect(Collectors.joining()
            );
            if(str.contains(card)){
                return cardList.get(0);
            }
        }
        return -1;
    }

    //是否飞机带对
    public static Integer isPlanePair(List<Integer> cardList){
        if(cardList.size()>9&&cardList.size()<21){
            List<Integer> plane = new ArrayList<>();
            int n = 0;
            List<Integer> pair = new ArrayList<>();
            int m = 0;
            for(int i = 0;i<cardList.size()-2;i++){
                if(getCardInfo(cardList.get(i)).getName()==getCardInfo(cardList.get(i+1)).getName()
                        &&getCardInfo(cardList.get(i)).getName()==getCardInfo(cardList.get(i+2)).getName()){
                    plane.add(cardList.get(i));
                    plane.add(cardList.get(i+1));
                    plane.add(cardList.get(i+2));
                    i+=2;
                    n++;
                }
            }
            if(isPlane(plane)!=-1){
                pair.addAll(cardList);
                pair.removeAll(plane);
                for(int i=0;i<pair.size()-1;i=i+2){
                    if(getCardInfo(cardList.get(i)).getName()==getCardInfo(cardList.get(i+1)).getName()){
                        m++;
                    }else{
                        return -1;
                    }
                }
            }
            if(m==n) return plane.get(0);
        }
        return -1;
    }

    //根据id,获取牌的信息
    public static Card getCardInfo(Integer id){
        return Cache.cardMap.get(id);
    }

}
