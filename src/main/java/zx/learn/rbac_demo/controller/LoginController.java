package zx.learn.rbac_demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import zx.learn.rbac_demo.entity.User;
import zx.learn.rbac_demo.service.UserService;
import zx.learn.rbac_demo.util.CacheSingleton;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LoginController {


    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;

    @Autowired
    CacheSingleton cache;


    @RequestMapping("login")
    public ModelAndView loginAction(String userName, String password) {

        String mdsPwd = DigestUtils.md5DigestAsHex((password+"zx").getBytes());

        ModelAndView mv = new ModelAndView();
        log.info("开始查询用户名为 " + userName + "和密码为" + mdsPwd + " 的用户 的数量");
        Integer count = userService.countUserByUserNameAndPassword(userName, mdsPwd);

        if (count > 0) {
            User user = userService.getUserInfoByUserName(userName);
            log.info("开始查询用户名为 " + userName + " 的用户  结果为 " + user);
            session.setAttribute("user", user);
            cache.loadResources(user);
            mv.setViewName("home");
            return mv;
        }else {
            log.info("开始查询用户名为 " + userName + "和密码为" + mdsPwd + " 的用户  结果为空");
            mv.addObject("error", "用户名或者密码错误");
            mv.setViewName("login");
            return mv;
        }
    }

    @RequestMapping("/")
    public String home(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        model.addAttribute("name", user.getUserName());
        return "home";
    }


    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "/login";
    }

}
