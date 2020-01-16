package com.ht.card.Controller;

import com.ht.card.Util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {
    @RequestMapping("/login.do")
    public String login(@RequestParam("userid") String userid ,@RequestParam("password") String password, Map<String,Object> map, HttpSession session){
        if(!StringUtil.isEmpty(userid)&&"123456".equals(password)){
            session.setAttribute("userid",userid);
            return "redirect:/main.html";
        }else{
            map.put("msg","用户名密码错误");
            return "login";
        }
    }
}
