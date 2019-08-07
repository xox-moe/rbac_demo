package zx.learn.rbac_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import zx.learn.rbac_demo.entity.Resource;
import zx.learn.rbac_demo.service.ResourceService;

import java.util.List;

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

    @RequestMapping("resourceList.html")
    public String groupList(Model model) {
        List<Resource> resourceList = resourceService.listAllResource();
        model.addAttribute("resourceList", resourceList);
        return "resource/resourceList";
    }


}
