package zx.learn.rbac_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import zx.learn.rbac_demo.entity.Resource;
import zx.learn.rbac_demo.entity.Role;
import zx.learn.rbac_demo.entity.User;
import zx.learn.rbac_demo.service.RoleService;
import zx.learn.rbac_demo.service.UserService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/7
 * Time: 14:32
 * Description:
 */
@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @RequestMapping("roleList.html")
    public String groupList(Model model) {
        List<Role> roleList = roleService.listAllRole();
        model.addAttribute("roleList", roleList);
        return "role/roleList";
    }


//    跳转到角色分配页面，选择角色，然后确认，就算是给用户分配角色了。
    @RequestMapping("allocateRoleForUserPage")
    public String allocateRoleForUser(Model model,@SessionAttribute("userId") Integer userId) {
        List<Role> roleList = roleService.listAllRole();
        List<Role> userRoleList = userService.listUserRoleByUserId(userId);
        model.addAttribute("roleList", roleList);
        model.addAttribute("userRoleList", userRoleList);
        return "role/roleListFouAllocate.html";
    }

    @RequestMapping("allocateRole.html")
    public String allocateRole(Model model) {
        List<User> userList = userService.listAllUser(null);
        model.addAttribute("userList", userList);
        return "role/allocateRole";
    }



}
