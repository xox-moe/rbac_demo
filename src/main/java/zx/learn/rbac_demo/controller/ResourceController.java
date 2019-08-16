package zx.learn.rbac_demo.controller;

import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import zx.learn.rbac_demo.annotation.SysLogs;
import zx.learn.rbac_demo.entity.Resource;
import zx.learn.rbac_demo.entity.Role;
import zx.learn.rbac_demo.service.ResourceService;
import zx.learn.rbac_demo.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/7
 * Time: 14:32
 * Description:
 */

@Controller
@RequestMapping("resource")
@Slf4j
public class ResourceController {


    @Autowired
    ResourceService resourceService;

    @Autowired
    RoleService roleService;

    @Autowired(required = false)
    ApplicationContext context;

    @SysLogs(name = "新增资源", type = "增加&跳转")
    @RequestMapping("addResource")
    public String addResource(String resourceName, String type, String url) {
        Resource resource = new Resource();
        resource.setResourceName(resourceName);
        resource.setType(type);
        resource.setUrl(url);
        resourceService.addResource(resource);
        return "redirect:/resource/resourceList.html";
    }

    @SysLogs(name = "删除资源", type = "删除&跳转")
    @RequestMapping("deleteResource")
    public String deleteResource(Integer id) {
        resourceService.deleteResource(id);
        return "redirect:/resource/resourceList.html";
    }

    @SysLogs(name = "资源列表页面", type = "查询&跳转")
    @RequestMapping("resourceList.html")
    public String groupList(Model model) {
        List<Resource> resourceList = resourceService.listAllResource();
        resourceList = resourceList.stream().sorted(Comparator.comparing(Resource::getUrl)).collect(Collectors.toList());
        model.addAttribute("resourceList", resourceList);
        return "resource/resourceList";
    }

    @SysLogs(name = "角色列表页面", type = "查询&跳转")
    @RequestMapping("roleListForAllocate.html")
    public String roleListForAllocatePage(Model model) {
        List<Role> roleList = roleService.listAllRole();
        model.addAttribute("roleList", roleList);
        return "resource/roleListForAllocate";
    }

    @SysLogs(name = "分配资源页面", type = "查询&跳转")
    @RequestMapping("allocateResourceForRole.html")
    public String allocateResourceForRolePage(Model model, Integer roleId) {
        List<Resource> resourceList = resourceService.listAllResource()
                .stream().sorted(Comparator.comparing(Resource::getUrl)).collect(Collectors.toList());
        List<Resource> roleResourceList = resourceService.listResourceForRole(roleId);
        model.addAttribute("resourceList", resourceList);
        model.addAttribute("roleResourceList", roleResourceList);
        model.addAttribute("roleId", roleId);
        return "resource/allocateResource";
    }


    @SysLogs(name = "分配资源", type = "删除&插入&跳转")
    @RequestMapping("allocateResourceForRole")
    public String allocateResourceForRole(Model model, Integer roleId, HttpServletRequest request) {

//        log.info("Context类："+request.getServletContext().getClass().toString());
        if (context != null) {
            context.toString();
        }


        String[] checkList = request.getParameterValues("check");
        List<Integer> resourceIdList = new ArrayList<>();
        for (String s : checkList) {
            resourceIdList.add(Integer.valueOf(s));
        }
        resourceService.addResourceToRole(roleId, resourceIdList);
        List<Resource> resourceList = resourceService.listAllResource();
        List<Resource> roleResourceList = resourceService.listResourceForRole(roleId);
        model.addAttribute("resourceList", resourceList);
        model.addAttribute("roleResourceList", roleResourceList);
        model.addAttribute("roleId", roleId);
        return "resource/allocateResource";
    }


}
