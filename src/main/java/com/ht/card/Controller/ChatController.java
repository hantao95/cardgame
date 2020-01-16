package com.ht.card.Controller;

import com.ht.card.entities.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @RequestMapping("/room")
    @ResponseBody
    public ResponseResult intoRoom(@RequestParam("roomid")String roomid, HttpSession session){
        session.setAttribute("roomid",roomid);
        ResponseResult rr = new ResponseResult<String>();
        if(roomid.equals("3")){
            rr.setData("/ht/gamemain.html");
        }else{
            rr.setData("/ht/sockettest1.html");
        }
        return rr;
    }
}