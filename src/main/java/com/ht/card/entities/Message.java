package com.ht.card.entities;

public class Message {
    private String type;
    private String message;

    public  Message(String type,String message){
        this.message = message;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
