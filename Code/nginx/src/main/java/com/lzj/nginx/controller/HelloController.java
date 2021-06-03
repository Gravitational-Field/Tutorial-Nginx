package com.lzj.nginx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * @ClassName controller
 * @Description: TODO
 * @Author Keen
 * @DATE 2021/6/3 16:11
 * @Version 1.0
 **/
@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello(HttpServletRequest request) {
        //return "helloworld";
        HttpSession session= request.getSession();
        int port2=request.getLocalPort();
        if(session.getAttribute("userid")==null){
            String userid= String.valueOf(new Random().nextInt(100)) ;
            session.setAttribute("userid", userid);
            //response.getWriter().append("Hello, "+userid+",this is "+port2+ " port");
            return "Hello, "+userid+",this is "+port2+ " port";
        }else{
            String userid=(String)session.getAttribute("userid");
            //response.getWriter().append("Welcome back, "+userid+", this is "+port2+" port") ;
            return "Welcome back, "+userid+", this is "+port2+" port";
        }
    }
}
