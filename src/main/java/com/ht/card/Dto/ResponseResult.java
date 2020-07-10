package com.ht.card.Dto;

public class ResponseResult<T> {
 private String message;
 private Integer state=200;
 private T data;
 
 
public T getData() {
	return data;
}
public void setData(T data) {
	this.data = data;
}
public ResponseResult() {
	super();
}
public ResponseResult(Exception ex, Integer state) {
	super();
	this.message = ex.getMessage();
	this.state = state;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Integer getState() {
	return state;
}
public void setState(Integer state) {
	this.state = state;
}
 
 
}
