package com.ht.card.Config;

import org.apache.catalina.session.StandardSessionFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import  javax.servlet.http.HttpSession ;
import  javax.websocket.HandshakeResponse ;
import  javax.websocket.server.HandshakeRequest ;
import  javax.websocket.server.ServerEndpointConfig ;

/**
 * NAME : WebChat/com.amayadream.webchat.websocket
 * Author : Amayadream
 * Date : 2016.01.12 17:10
 * TODO :
 */
@Configuration
public  class  HttpSessionConfigurator  extends  ServerEndpointConfig . Configurator   {

    @Override
    public  void  modifyHandshake ( ServerEndpointConfig  sec , HandshakeRequest  request , HandshakeResponse  response ){
        /*如果没有监听器,那么这里获取到的HttpSession是null*/
        StandardSessionFacade ssf = (StandardSessionFacade) request.getHttpSession();
        if (ssf != null) {
            HttpSession session = (HttpSession) request.getHttpSession();
            sec.getUserProperties().put("session", session);
        }
        sec.getUserProperties().put("name", "小强");
        super.modifyHandshake(sec, request, response);
    }
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        //这个对象说一下，貌似只有服务器是tomcat的时候才需要配置,具体我没有研究
        return new ServerEndpointExporter();
    }
}