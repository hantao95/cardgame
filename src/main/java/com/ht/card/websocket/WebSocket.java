package com.ht.card.websocket;


import com.ht.card.Config.HttpSessionConfigurator;
import com.ht.card.Constant.CardConstant;
import com.ht.card.Util.StringUtil;
import com.ht.card.entities.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
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
    //房间，牌组
    public static Map<String,GameInfo> gameInfoMap = new ConcurrentHashMap<>();
    //房间，抢地主状态
    public static Map<String,LandownerInfo> landownerMap = new ConcurrentHashMap<>();
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
                //一个房间的人数
                userlist = new String[3];
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
                    for(int i=0;i<userlist.length;i++){
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
            ReadyMessage readyMessage = new ReadyMessage(userid,roomid,seatid,"not ready");
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
                    if(StringUtil.isEmpty(user)){
                        continue;
                    }
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
                //给房间所有人发送聊天信息
                for(String user:roomMap.get(roomid)){
                    if(StringUtil.isEmpty(user))
                        continue;
                    userMap.get(user).getSession().getBasicRemote().sendText("chat@"+chatinfo.toString());
                }
            }else if(CardConstant.messagetype_ready.equals(body.getType())){
                 ReadyMessage readyInfo = MessageResolve.ResolveReady(body.getMessage());
                 statusMap.put(readyInfo.getUserid(),readyInfo);
                 String roomid = readyInfo.getRoomid();
                boolean gamestart = true;
                //给房间所有人发送准备信息
                for(String user:roomMap.get(roomid)){
                    if(StringUtil.isEmpty(user)){
                        gamestart = false;
                        continue;
                    }
                    userMap.get(user).getSession().getBasicRemote().sendText("ready@"+readyInfo.toString());
                    if ("not ready".equals(statusMap.get(user).getStatus())){
                        gamestart = false;
                    }
                }
                if(gamestart){
                    GameInfo gameInfo = new GameInfo();
                    gameInfoMap.put(roomid,gameInfo);
                    //给房间所有人发送游戏开始
                    sendMessage(roomid,gameInfo,"start@");
                    //给房间所有人发送手牌信息
                    showhandcard(roomid,gameInfo);
                }
            }else if(CardConstant.getMessagetype_landowner.equals(body.getType())){
                LandownerMessage landownerMessage = MessageResolve.ResolveLandowner(body.getMessage());
                String roomid = landownerMessage.getRoomid();
                LandownerInfo landownerInfo = new LandownerInfo();
                if (landownerMap.get(roomid)!=null){
                    landownerInfo=landownerMap.get(roomid);
                }else{
                    landownerMap.put(roomid,landownerInfo);
                }
                landownerInfo.doLandowner(landownerMessage);
                //将底牌给地主,然后刷新手牌
                if(landownerInfo.getLandowner()!=-1&&landownerInfo.getLandowner()!=-10000){
                    gameInfoMap.get(roomid).landowner(landownerInfo.getLandowner());
                    showhandcard(roomid,gameInfoMap.get(roomid));
                }
                //给房间所有人发送抢地主信息
                for(String user:roomMap.get(roomid)){
                    if(StringUtil.isEmpty(user)){
                        continue;
                    }
                    SessionInfo sInfo = userMap.get(user);
                    sInfo.getSession().getBasicRemote().sendText("landowner@"+landownerInfo.toString());
                }
                if(landownerInfo.getLandowner()!=-1){
                    landownerInfo.clear(); //一轮抢地主结束后 清空记录
                }
            }else if(CardConstant.messagetype_play.equals(body.getType())){
                GameMessage gameMessage = MessageResolve.ResolveGame(body.getMessage());
                String roomid = gameMessage.getRoomid();
                String userid = gameMessage.getUserid();
                Integer seatid = gameMessage.getSeatid();
                List<Integer> putoutCard = gameMessage.getCardList();
                CardType putCardType = GameLogic.checkcard(putoutCard);
                if(putCardType==null&&CardType.CardType_Null.equals(gameInfoMap.get(roomid).getCardType())){
                    //出牌失败
                    SessionInfo sInfo = userMap.get(userid);
                    sInfo.getSession().getBasicRemote().sendText("putcard@error_0@"+gameMessage.getSeatid()+"@不会玩就别玩");
                }else if(putCardType!=null&&CardType.CardType_Null.equals(putCardType)){//不要
                    GameInfo gameinto = gameInfoMap.get(roomid);
                    gameinto.passtimeAdd();
                    String passtime = "pass_1";
                    if(gameinto.checkPass()){
                        passtime = "pass_2";
                        gameinto.passtimeClear();
                        gameinto.setCardType(CardType.CardType_Null);
                    }
                    for(String user:roomMap.get(roomid)){
                        if(StringUtil.isEmpty(user)){
                            continue;
                        }
                        SessionInfo sInfo = userMap.get(user);
                        sInfo.getSession().getBasicRemote().sendText("putcard@"+passtime+"@"+gameMessage.getSeatid()+"@-10000");
                    }
                }else{
                    if(gameInfoMap.get(roomid).putoutcard(seatid,putoutCard,putCardType)){
                        //出牌成功
                        gameInfoMap.get(roomid).passtimeClear();
                        for(String user:roomMap.get(roomid)){
                            if(StringUtil.isEmpty(user)){
                                continue;
                            }
                            SessionInfo sInfo = userMap.get(user);
                            sInfo.getSession().getBasicRemote().sendText("putcard@success@"+gameMessage.getSeatid()+"@"+gameMessage.getCards());
                        }
                    }else{
                        //出牌失败
                        SessionInfo sInfo = userMap.get(userid);
                        sInfo.getSession().getBasicRemote().sendText("putcard@error_1@"+gameMessage.getSeatid()+"@牌型不对");
                    }
                }
                showhandcard(roomid,gameInfoMap.get(roomid));

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
        System.out.println(error.getMessage());
        error.printStackTrace();
    }

    /**
     * 发送手牌信息
     */
    public void showhandcard(String roomid,GameInfo gameInfo) throws IOException{
        for(String user:roomMap.get(roomid)){
            if(StringUtil.isEmpty(user)){
                continue;
            }
            SessionInfo sInfo = userMap.get(user);
            sInfo.getSession().getBasicRemote().sendText("card@"+gameInfo.getPlayList(sInfo.getSeatid())+"@"+gameInfo.getCardNumber());
        }
    }

    /**
     * 给房间所有人发送信息
     */
    public void sendMessage(String roomid,GameInfo gameInfo,String message) throws IOException{
        for(String user:roomMap.get(roomid)){
            if(StringUtil.isEmpty(user)){
                continue;
            }
            SessionInfo sInfo = userMap.get(user);
            sInfo.getSession().getBasicRemote().sendText(message);
        }
    }
}