package com.ht.card.websocket;


import com.ht.card.Config.HttpSessionConfigurator;
import com.ht.card.entities.Message;
import com.ht.card.entities.SessionInfo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: websocket
 */
@ServerEndpoint(value = "/websocket/{roomid}", configurator = HttpSessionConfigurator.class)
@Component
public class WebSocket {

    private static final String loggerName=WebSocket.class.getName();
    //客户，客户信息
    public static Map<String,SessionInfo> userMap = new ConcurrentHashMap<>();
    //房间号，客户
    public static Map<String,List<String>> room = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("roomid") String roomid, Session session,EndpointConfig config) {
        HttpSession hse = (HttpSession)config.getUserProperties().get("session");
        String userid = (String) hse.getAttribute("userid");
        List<String> userlist = room.get(roomid);
        try {
            if(null==userlist){
                //一个房间最多4个人
                userlist = new ArrayList<>(4);
                userlist.add(userid);
            }else{
                if(userlist.size()==4){
                        session.getBasicRemote().sendText("已经满了");
                        session.close();
                        return;
                }else{
                    if(userMap.get(userid)!=null){
                        userMap.get(userid).getSession().getBasicRemote().sendText("你已在别处登录");
                        userMap.get(userid).getSession().close();
                    }
                    userlist.add(userid);
                }
            }
            room.put(roomid,userlist);
            userMap.put(userid,new SessionInfo(userid,roomid,session));
            //发送进入提示
            for(String id:userlist){
                userMap.get(id).getSession().getBasicRemote().sendText(userid+"进入房间");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("roomid") String roomid,Session session) {
        if (room.containsKey(roomid)){
            String userid ="";
            for(String user :room.get(roomid)){
                if(userMap.get(user).getSession()==session){
                    userid = user;
                }
            }
            userMap.remove(userid);
            room.get(roomid).remove(userid);
            try{
                for(String user:room.get(roomid)){
                    userMap.get(user).getSession().getBasicRemote().sendText(userid+"退出了");
                }
            }catch (IOException e){
                e.printStackTrace();
        }

        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        Message body = MessageResolve.Resolve(message);
            String userid = body.getUserid();
            String info = body.getMessage();
            String roomid = body.getroomid();
            try{
                for(String user:room.get(roomid)){
                    userMap.get(user).getSession().getBasicRemote().sendText(userid+":"+info);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
    }
}