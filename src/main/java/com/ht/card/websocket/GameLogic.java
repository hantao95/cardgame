package com.ht.card.websocket;

import com.ht.card.Util.StringUtil;

import java.util.*;

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

    public void checkcard(){

    }



}
