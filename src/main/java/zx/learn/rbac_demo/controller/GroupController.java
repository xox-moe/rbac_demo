package zx.learn.rbac_demo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zx.learn.rbac_demo.entity.Group;
import zx.learn.rbac_demo.entity.ReturnBean;
import zx.learn.rbac_demo.entity.Role;
import zx.learn.rbac_demo.entity.User;
import zx.learn.rbac_demo.service.GroupService;
import zx.learn.rbac_demo.service.UserService;
import zx.learn.rbac_demo.util.MapBeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        if (user == null)
            user = new User();
        List<User> userList = userService.listAllUser(user);
        List<Map<String, String>> result = new ArrayList<>();
        for (User user1 : userList) {
            List<Role> userRoleList = userService.listUserRoleByUserId(user1.getUserId());
//            StringBuilder stringBuilder = new StringBuilder();
//            for (Role role : userRoleList) {
//                stringBuilder.append(role.getRoleName()).append(",");
//            }
            String roleStr = StringUtils.join(userRoleList.parallelStream().map(Role::getRoleName).toArray(),",") ;
//            userRoleList.forEach( r-> stringBuilder.append(r.getRoleName()).append(","));
            Map<String, String> user2 = MapBeanUtil.object2Map(user1);
            user2.put("roleStr",roleStr);
            result.add(user2);
        }

        model.addAttribute("userList", result);
        return "group/userList";
//        return ReturnBean.getSuccess("success", userList, userList.size());
    }

    @RequestMapping("groupList.html")
    public String groupList(Model model) {
        List<Group> groupList = groupService.listAllGroup();
        model.addAttribute("groupList", groupList);
        return "group/groupList";
    }

    @RequestMapping("addGroup")
    @ResponseBody
    public ReturnBean addGroup(String groupName) {
        if (groupService.addGroup(new Group(null, groupName))) {
            return ReturnBean.getSuccess("success");
        } else {
            return ReturnBean.getFailed("失败");
        }
    }
    @RequestMapping("deleteGroupById")
    public String deleteGroupById(Integer id) {
        groupService.deleteGroup(id);
        return "redirect:/group/groupList.html";
    }

}
