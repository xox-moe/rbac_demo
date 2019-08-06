package zx.learn.rbac_demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TempleController {


    @RequestMapping("home")
    public String home(){
        return "home";
    }

    @RequestMapping("messageList")
    public String messageList(){
        return "messageList";
    }

    @RequestMapping("userList")
    public String userList(){
        return "userList";
    }

    @RequestMapping("newMessage")
    public String newMessage(){

        return "newMessage";
    }






}
