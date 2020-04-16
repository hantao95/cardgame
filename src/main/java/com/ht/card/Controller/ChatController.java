package com.ht.card.Controller;

import com.ht.card.entities.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @RequestMapping("/room")
    @ResponseBody
    public ResponseResult intoRoom(@RequestParam("roomid")String roomid, HttpSession session){
        session.setAttribute("roomid",roomid);
        ResponseResult rr = new ResponseResult<String>();
        rr.setData("/ht/gamemain.html");
        return rr;
    }
}
