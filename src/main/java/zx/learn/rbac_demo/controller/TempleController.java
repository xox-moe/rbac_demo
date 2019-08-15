package zx.learn.rbac_demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import zx.learn.rbac_demo.annotation.SysLogs;

@Controller
public class TempleController {


    @SysLogs(name = "主页", type = "跳转")
    @RequestMapping("home.html")
    public String home() {
        return "common/index";
    }

    @SysLogs(name = "主页", type = "跳转")
    @RequestMapping("common/index.html")
    public String index() {
        return "common/index";
    }

    @SysLogs(name = "错误页面", type = "跳转")
    @RequestMapping("common/error.html")
    public String error() {
        return "common/error";
    }

    @SysLogs(name = "留言列表", type = "跳转")
    @RequestMapping("message/messageList.html")
    public String messageList() {
        return "message/messageList";
    }

    @SysLogs(name = "修改密码页面", type = "跳转")
    @RequestMapping("user/changePassword.html")
    public String changePassword() {
        return "user/changePassword";
    }

    @SysLogs(name = "留言列表新", type = "跳转")
    @RequestMapping("message/messageListTest.html")
    public String messageListTest() {
        return "message/messageListTest";
    }

    @SysLogs(name = "无权限页面", type = "跳转")
    @RequestMapping("common/noPermission.html")
    public String noPermission() {
        return "common/noPermission";
    }

    @SysLogs(name = "用户列表页面跳转", type = "跳转")
    @RequestMapping("user/userList.html")
    public String userList() {
        return "group/userList";
    }


}
