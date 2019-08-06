package zx.learn.rbac_demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zx.learn.rbac_demo.entity.Resource;
import zx.learn.rbac_demo.entity.User;
import zx.learn.rbac_demo.service.UserService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/6
 * Time: 8:51
 * Description:
 */

@Component()
public class CacheSingleton {

    @Autowired
    UserService userService;

    public HashMap<Integer, HashMap<String, Object>> userResource;

    {
        this.userResource = new HashMap<>();
    }

    public HashMap<String, Object> getResourceByUserId(Integer userId) {
        if (this.userResource.containsKey(userId)) {
            return this.userResource.get(userId);
        } else {
            return this.userResource.put(userId, new HashMap<>());
        }
    }

    public HashMap<String, Object> getResourceByUserId(Integer userId, Boolean ifCreate) {
        if (ifCreate) {
            return getResourceByUserId(userId);
        } else {
            return this.userResource.get(userId);
        }
    }


    /**
     * 这里 进行加载该用户的资源。主要是有哪些权限。
     *
     * @param user
     */
    public void loadResources(User user) {
        HashMap<String, Object> map = this.getResourceByUserId(user.getUserId());
        List<Resource> resources = userService.listResourceByUserId(user.getUserId());
        map.put("resourceList", resources);
    }

    public void reloadResources(Integer userId) {
        HashMap<String, Object> map = this.getResourceByUserId(userId);
        List<Resource> resources = userService.listResourceByUserId(userId);
        map.put("resourceList", resources);
    }
}
