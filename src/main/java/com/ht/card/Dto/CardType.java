package com.ht.card.Dto;

//打出的牌型
public class CardType {

    private String type = "N";
    private Integer sort = 0;
    /**没有牌型**/
    public static CardType CardType_Null = new CardType();

    public CardType(String type, Integer sort) {
        this.type = type;
        this.sort = sort;
    }

    public CardType() {
        this.type = "N";
        this.sort = 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean equals(CardType cardType){
        return type.equals(cardType.getType());
    }

    //大于返回true，小于返回false
    public Boolean morethan(CardType cardType){
        if(this.equals(cardType)){
            if(this.sort.compareTo(cardType.getSort())==1){
                return true;
            }
        }
        return false;
    }
}
