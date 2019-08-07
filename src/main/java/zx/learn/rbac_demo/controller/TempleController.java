package zx.learn.rbac_demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TempleController {


    @RequestMapping("home.html")
    public String home(){
        return "common/index";
    }

    @RequestMapping("common/index.html")
    public String index(){
        return "common/index";
    }

    @RequestMapping("message/messageList.html")
    public String messageList(){
        return "message/messageList";
    }


    @RequestMapping("message/messageListTest.html")
    public String messageListTest(){
        return "message/messageListTest";
    }


    @RequestMapping("common/noPermission.html")
    public String noPermission(){
        return "common/noPermission";
    }

    @RequestMapping("user/userList.html")
    public String userList(){
        return "group/userList";
    }


}
