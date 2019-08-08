package zx.learn.rbac_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import zx.learn.rbac_demo.entity.Role;
import zx.learn.rbac_demo.entity.User;
import zx.learn.rbac_demo.service.RoleService;
import zx.learn.rbac_demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @RequestMapping("addRole")
    public String addRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        roleService.addRole(role);
        return "redirect:/role/roleList.html";
    }

    @RequestMapping("deleteRole")
    public String deleteRole(Integer id) {
        roleService.deleteRole(id);
        return "redirect:/role/roleList.html";
    }




    //    跳转到角色分配页面，选择角色，然后确认，就算是给用户分配角色了。
    @RequestMapping("allocateRoleForUser.html")
    public String allocateRoleForUserPage(Model model, Integer id) {
        List<Role> roleList = roleService.listAllRole();
        List<Role> userRoleList = userService.listUserRoleByUserId(id);
        model.addAttribute("roleList", roleList);
        model.addAttribute("userId", id);
        model.addAttribute("userRoleList", userRoleList);
        return "role/allocateRole";
    }

    @RequestMapping("allocateRoleForUser")
    public String allocateRoleForUser(Model model, Integer userId, HttpServletRequest request) {
        String[] checkList = request.getParameterValues("check");
        if (checkList == null)
            checkList = new String[0];
        List<Integer> roleIdList = new ArrayList<>();
        for (String s : checkList) {
            roleIdList.add(Integer.valueOf(s));
        }
        roleService.addRoleToUser(userId, roleIdList);
        List<Role> roleList = roleService.listAllRole();
        List<Role> userRoleList = userService.listUserRoleByUserId(userId);
        model.addAttribute("roleList", roleList);
        model.addAttribute("userRoleList", userRoleList);
        return "role/allocateRole";
    }


    @RequestMapping("userListForAllocate.html")
    public String allocateRole(Model model) {
        List<User> userList = userService.listAllUser(null);
        model.addAttribute("userList", userList);
        return "role/userListForAllocate";
    }


}
