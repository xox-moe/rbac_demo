package zx.learn.rbac_demo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import zx.learn.rbac_demo.annotation.SysLogs;
import zx.learn.rbac_demo.entity.Role;
import zx.learn.rbac_demo.entity.User;
import zx.learn.rbac_demo.service.RoleService;
import zx.learn.rbac_demo.service.UserService;
import zx.learn.rbac_demo.util.MapBeanUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @SysLogs(name = "角色列表", type = "查询&跳转")
    @RequestMapping("roleList.html")
    public String roleList(Model model) {
        List<Role> roleList = roleService.listAllRole();
        model.addAttribute("roleList", roleList);
        return "role/roleList";
    }

    @SysLogs(name = "新增角色", type = "新增&跳转")
    @RequestMapping("addRole")
    public String addRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        roleService.addRole(role);
        return "redirect:/role/roleList.html";
    }

    @SysLogs(name = "删除角色", type = "删除&跳转")
    @RequestMapping("deleteRole")
    public String deleteRole(Integer id,Model model) {
        try {
            roleService.deleteRole(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/common/error";
        }
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
//        model.addAttribute("userList", userList);
        List<Map<String, String>> result = new ArrayList<>();
        for (User user1 : userList) {
            List<Role> userRoleList = userService.listUserRoleByUserId(user1.getUserId());
            String roleStr = StringUtils.join(userRoleList.parallelStream().map(Role::getRoleName).toArray(), ",");
            Map<String, String> user2 = MapBeanUtil.object2Map(user1);
            user2.put("roleStr", roleStr);
            result.add(user2);
        }

        model.addAttribute("userList", result);
        return "role/userListForAllocate";
    }


}
