package com.ht.card.websocket;


import com.ht.card.Config.HttpSessionConfigurator;
import com.ht.card.Constant.CardConstant;
import com.ht.card.Util.StringUtil;
import com.ht.card.entities.ChatMessage;
import com.ht.card.entities.Message;
import com.ht.card.entities.ReadyMessage;
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
    public static Map<String,String[]> roomMap = new ConcurrentHashMap<>();
    //客户，状态
    public static Map<String,ReadyMessage> statusMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("roomid") String roomid, Session session,EndpointConfig config) {
        HttpSession hse = (HttpSession)config.getUserProperties().get("session");
        String userid = (String) hse.getAttribute("userid");
        int seatid = -1;
        String[] userlist = roomMap.get(roomid);
        try {
            if(null==userlist){
                //一个房间最多4个人
                userlist = new String[4];
                userlist[0]=userid;
                seatid = 0;
            }else{
                if(!StringUtil.strsEmplty(userlist)){
                        session.getBasicRemote().sendText("chat@"+"已经满了");
                        session.close();
                        return;
                }else{
                    if(userMap.get(userid)!=null){
                        userMap.get(userid).getSession().getBasicRemote().sendText("chat@"+"你已在别处登录");
                        userMap.get(userid).getSession().close();
                    }
                    for(int i=0;i<4;i++){
                        if(StringUtil.isEmpty(userlist[i])){
                            userlist[i] = userid;
                            seatid = i;
                            break;
                        }
                    }
                }
            }
            roomMap.put(roomid,userlist);
            userMap.put(userid,new SessionInfo(userid,roomid,seatid,session));
            ReadyMessage readyMessage = new ReadyMessage(userid,roomid,seatid,"not Ready");
            statusMap.put(userid,readyMessage);
            //发送进入提示，并且对新加入的连接发送当前所有人的准备状态
            for(String id:userlist){
                if(StringUtil.isEmpty(id))
                    continue;
                Session uSession = userMap.get(id).getSession();
                uSession.getBasicRemote().sendText("chat@"+userid+"进入房间");
                uSession.getBasicRemote().sendText("ready@"+readyMessage.toString());
                session.getBasicRemote().sendText("ready@"+statusMap.get(id).toString());
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
        boolean isnew = true;
        for(Map.Entry<String,SessionInfo> user: userMap.entrySet()){
             if(user.getValue().getSession() ==session){
                 isnew = false;
             }
        }
        if(isnew)
            return;
        if (roomMap.containsKey(roomid)){
            String userid ="";
            int seatid = -1;
            for(String user :roomMap.get(roomid)){
                if(StringUtil.isEmpty(user))
                    continue;;
                if(userMap.get(user).getSession()==session){
                    userid = user;
                    seatid = userMap.get(user).getSeatid();
                }
            }
            userMap.remove(userid);
            (roomMap.get(roomid))[seatid]=null;
            ReadyMessage ready = statusMap.get(userid);
            ready.setUserid("玩家"+(ready.getSeatid()+1));
            ready.setStatus("无");
            try{
                for(String user:roomMap.get(roomid)){
                    if(StringUtil.isEmpty(user))
                        continue;;
                    userMap.get(user).getSession().getBasicRemote().sendText("chat@"+userid+"退出了");
                    userMap.get(user).getSession().getBasicRemote().sendText("ready@"+ready.toString());
                }
                statusMap.remove(userid);
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
        try{
            if (CardConstant.messagetype_chat.equals(body.getType())){
                ChatMessage chatinfo = MessageResolve.ResolveChat(body.getMessage());
                String roomid = chatinfo.getroomid();

                    for(String user:roomMap.get(roomid)){
                        if(StringUtil.isEmpty(user))
                            continue;;
                        userMap.get(user).getSession().getBasicRemote().sendText("chat@"+chatinfo.toString());
                    }
            }else if(CardConstant.messagetype_ready.equals(body.getType())){
                 ReadyMessage readyInfo = MessageResolve.ResolveReady(body.getMessage());
                 statusMap.put(readyInfo.getUserid(),readyInfo);
                 String roomid = readyInfo.getRoomid();
                for(String user:roomMap.get(roomid)){
                    if(StringUtil.isEmpty(user))
                        continue;;
                    userMap.get(user).getSession().getBasicRemote().sendText("ready@"+readyInfo.toString());
                }
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