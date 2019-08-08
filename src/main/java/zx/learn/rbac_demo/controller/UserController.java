package zx.learn.rbac_demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zx.learn.rbac_demo.entity.Resource;
import zx.learn.rbac_demo.entity.ReturnBean;
import zx.learn.rbac_demo.entity.User;
import zx.learn.rbac_demo.service.UserService;
import zx.learn.rbac_demo.util.CacheSingleton;


import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@Slf4j
@RequestMapping("user")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    CacheSingleton cache;


//
//    @RequestMapping("register")
//    public String register(String userName, String email, String password, String phone) {
//        User user = new User(null, userName, password, email, phone);
//        userService.(user);
//        return "home";
//    }

    @RequestMapping("myInfo.html")
    public String myInfo(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "user/myInfo";
    }

    @RequestMapping("editMyInfo.html")
    public String editMyInfo(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "user/editMyInfo";
    }

    @RequestMapping("editMyInfo")
    public String editMyInfo(Model model, HttpSession session, User user) {
        User oldUser = (User) session.getAttribute("user");

        StringBuilder error = new StringBuilder();
        if (!user.getUserId().equals(oldUser.getUserId())) {
            error.append("请修改自己的信息，你没有权限修改其他用户的信息");
        } else {
            userService.updateUserInfo(user);
        }
        model.addAttribute("user", user);
        session.setAttribute("user", user);
        model.addAttribute("errorInfo", error.toString());
        return "user/myInfo";
    }

    @RequestMapping("changePassword")
    @ResponseBody
    public ReturnBean changePassword(String oldPassword, String newPassword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        //不打算改密码

//                验证旧的密码是否错误
        int count = userService.countUserByUserNameAndPassword(user.getUserName(), oldPassword);

        if (count == 0) {
            return ReturnBean.getFailed("旧密码错误");
        } else {
            userService.updateUserPassword(user.getUserId(), newPassword);
            session.invalidate();
            return ReturnBean.getSuccess("修改成功");
        }


    }


    @RequestMapping("listMyResource.html")
    public String listMyResource(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map map = cache.getResourceByUserId(user.getUserId());
        Set<Resource> resourceList = new HashSet<>((List<Resource>) map.get("resourceList"));
        model.addAttribute("resourceList", resourceList);
        return "user/listMyResource";
    }

    @RequestMapping("addUser")
    public String addUser(Model model, User user) {
        userService.registerUser(user);
        return "redirect:/group/userList.html";
    }

    @RequestMapping("deleteUserById")
    public String deleteUserById(Integer id) {
        userService.deleteUser(id);
        return "redirect:/group/userList.html";
    }

}
