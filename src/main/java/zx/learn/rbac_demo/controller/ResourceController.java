package zx.learn.rbac_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class ResourceController {


    @Autowired
    ResourceService resourceService;

    @Autowired
    RoleService roleService;

    @RequestMapping("addResource")
    public String addResource(String resourceName, String type, String url) {
        Resource resource = new Resource();
        resource.setResourceName(resourceName);
        resource.setType(type);
        resource.setUrl(url);
        resourceService.addResource(resource);
        return "redirect:/resource/resourceList.html";
    }

    @RequestMapping("deleteResource")
    public String deleteResource(Integer id) {
        resourceService.deleteResource(id);
        return "redirect:/resource/resourceList.html";
    }


    @RequestMapping("resourceList.html")
    public String groupList(Model model) {
        List<Resource> resourceList = resourceService.listAllResource();
        model.addAttribute("resourceList", resourceList);
        return "resource/resourceList";
    }

    @RequestMapping("roleListForAllocate.html")
    public String roleListForAllocatePage(Model model) {
        List<Role> roleList = roleService.listAllRole();
        model.addAttribute("roleList", roleList);
        return "resource/roleListForAllocate";
    }

    @RequestMapping("allocateResourceForRole.html")
    public String allocateResourceForRolePage(Model model, Integer roleId) {
        List<Resource> resourceList = resourceService.listAllResource().stream().sorted( Comparator.comparing(Resource::getUrl)).collect(Collectors.toList());
        List<Resource> roleResourceList = resourceService.listResourceForRole(roleId);
        model.addAttribute("resourceList", resourceList);
        model.addAttribute("roleResourceList", roleResourceList);
        model.addAttribute("roleId", roleId);
        return "resource/allocateResource";
    }


    @RequestMapping("allocateResourceForRole")
    public String allocateResourceForRole(Model model, Integer roleId, HttpServletRequest request) {
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
