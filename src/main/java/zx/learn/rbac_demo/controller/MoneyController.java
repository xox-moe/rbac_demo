package zx.learn.rbac_demo.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import zx.learn.rbac_demo.annotation.SysLogs;
import zx.learn.rbac_demo.model.Record;
import zx.learn.rbac_demo.model.ReturnBean;
import zx.learn.rbac_demo.model.User;
import zx.learn.rbac_demo.service.MoneyService;

import javax.servlet.http.HttpSession;

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
    @SysLogs(name = "充值", type ="更新")
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
    @SysLogs(name = "打赏", type ="查询&更新")
    public ReturnBean reward(@SessionAttribute("userId") Integer fromId, Integer toId, double amount) {
        try {
            moneyService.transfer(fromId, toId, amount);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnBean.getFailed("打赏失败，原因：" + e.getMessage());
        }
        return ReturnBean.getSuccess("打赏成功");
    }


    //    public ReturnBean
    @RequestMapping("listUserTransferRecord")
    @ResponseBody
    public ReturnBean listUserTransferRecord(@SessionAttribute("userId") Integer userId,
                                             @RequestParam(required = false, defaultValue = "1") Integer page,
                                             @RequestParam(required = false, defaultValue = "5") Integer limit) {

        Page<Record> recordList = moneyService.listUserTransferRecord(userId, page, limit);

        return ReturnBean.getSuccess("success", recordList, recordList.getTotal());
    }

    @RequestMapping("listUserTransferRecordPage")
    @SysLogs(name = "查看我的资金记录", type ="查询")
    public String listUserTransferRecordPage(@SessionAttribute("userId") Integer userId,
                                             Model model,
                                             @RequestParam(required = false, defaultValue = "1") Integer page,
                                             @RequestParam(required = false, defaultValue = "15") Integer limit) {

        Page<Record> recordList = moneyService.listUserTransferRecord(userId, page, limit);
        model.addAttribute("recordList", recordList);
        model.addAttribute("pageNum", recordList.getPages());
        model.addAttribute("page", page);
        model.addAttribute("limit", limit);
        return "user/userTransferRecord";
    }
}
