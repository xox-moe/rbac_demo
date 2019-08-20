package zx.learn.rbac_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import zx.learn.rbac_demo.model.ReturnBean;
import zx.learn.rbac_demo.model.User;
import zx.learn.rbac_demo.service.MoneyService;

import javax.servlet.http.HttpSession;
import java.lang.annotation.Retention;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/20
 * Time: 15:18
 * Description:
 */

@Controller
@RequestMapping("money")
public class MoneyController {


    @Autowired
    MoneyService moneyService;

    @Autowired
    HttpSession session;


    @RequestMapping("recharge")
    @ResponseBody
    public ReturnBean recharge(@SessionAttribute("userId") Integer userId, double amount) {
        User user = (User) session.getAttribute("user");
        if (moneyService.recharge(userId, amount)) {
            user.setUserBalance(user.getUserBalance() + amount);
            return ReturnBean.getSuccess("充值成功");
        } else {
            return ReturnBean.getFailed("充值失败");
        }
    }

    @RequestMapping("reward")
    @ResponseBody
    public ReturnBean reward(@SessionAttribute("userId") Integer fromId, Integer toId, double amount) {
        User user = (User) session.getAttribute("user");
        try {
            moneyService.transfer(fromId, toId, amount);
            user.setUserBalance(user.getUserBalance() - amount);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnBean.getFailed("打赏失败，原因：" + e.getMessage());
        }
        return ReturnBean.getSuccess("打赏成功");
    }


    //    public ReturnBean
//    public ReturnBean listUser() {
//        moneyService.
//    }
}
