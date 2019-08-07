package zx.learn.rbac_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zx.learn.rbac_demo.entity.Group;
import zx.learn.rbac_demo.entity.ReturnBean;
import zx.learn.rbac_demo.entity.User;
import zx.learn.rbac_demo.service.GroupService;
import zx.learn.rbac_demo.service.UserService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/7
 * Time: 14:04
 * Description:
 */

@Controller
@RequestMapping("group")
public class GroupController {

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @RequestMapping("userList.html")
    public String listUser(@RequestParam(required = false) User user, Model model) {
        List<User> userList = userService.listAllUser(user);
        model.addAttribute("userList", userList);
        return "group/userList";
//        return ReturnBean.getSuccess("success", userList, userList.size());
    }

    @RequestMapping("groupList.html")
    public String groupList(Model model) {
        List<Group> groupList = groupService.listAllGroup();
        model.addAttribute("groupList", groupList);
        return "group/groupList";
    }


}
