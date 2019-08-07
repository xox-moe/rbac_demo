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


    @RequestMapping("/login")
    public ModelAndView loginAction(String userName, String password) {

        ModelAndView mv = new ModelAndView();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser != null) {
            if (currentUser.getUserName().equals(userName)) {
                log.info("用户已经登陆过了，直接跳过登录。");
                mv.setViewName("common/index");
                return mv;
            }
        }

        String mdsPwd = DigestUtils.md5DigestAsHex((password+"zx").getBytes());

        log.info("开始查询用户名为 " + userName + "和密码为" + password + " 的用户 的数量");
        Integer count = userService.countUserByUserNameAndPassword(userName, password);

        log.info("根据用户名和密码 找到了"+count+"个匹配用户");
        if (count > 0) {
            User user = userService.getUserInfoByUserName(userName);
            log.info("开始查询用户名为 " + userName + " 的用户  结果为 " + user);
            session.setAttribute("user", user);
            cache.loadResources(user);
            mv.setViewName("common/index");
            return mv;
        }else {
            log.info("开始查询用户名为 " + userName + "和密码为" + mdsPwd + " 的用户  结果为空");
            mv.addObject("error", "用户名或者密码错误");
            mv.setViewName("user/login");
            return mv;
        }
    }

    @RequestMapping("/")
    public String home(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "user/login";
        }
        model.addAttribute("name", user.getUserName());
        return "common/index";
    }


    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "user/login";
    }

}