package com.ht.card.Controller;

import com.ht.card.Dto.ResponseResult;
import com.ht.card.Util.StringUtil;
import com.ht.card.entities.UserInfo;
import com.ht.card.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping("/login.do")
    public String login(@RequestParam("userid") String userid ,@RequestParam("password") String password, Map<String,Object> map, HttpSession session){

        UserInfo  userInfo = userService.getUserInfo(userid);
        UserInfo  userRInfo = userService.getUserRInfo(userid);

        if(!StringUtil.isEmpty(userid)&&"123456".equals(password)){
            session.setAttribute("userid",userid);
            return "redirect:/main.html";
        }else{
            map.put("msg","用户名密码错误");
            return "login";
        }
    }

    @ResponseBody
    @RequestMapping("/register.do")
    public ResponseResult register(@RequestBody UserInfo userInfo){
        userService.save(userInfo);
        ResponseResult rr = new ResponseResult<String>();
        rr.setData("success");
        return rr;
    }
}
