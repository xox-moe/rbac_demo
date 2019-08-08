package zx.learn.rbac_demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zx.learn.rbac_demo.entity.Group;
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
@Slf4j
public class CacheSingleton {

    @Autowired
    UserService userService;

    public static CacheSingleton cacheSingleton;

    public HashMap<Integer, HashMap<String, Object>> userResource;

    {
        this.userResource = new HashMap<>();
        log.info("权限缓存单例进行了初始化");
        CacheSingleton.cacheSingleton = this;
    }

    public HashMap<String, Object> getResourceByUserId(Integer userId) {
        if (this.userResource.containsKey(userId)) {
            HashMap<String, Object> map = this.userResource.get(userId);
//            if (map == null) {
//            return this.userResource.put(userId, new HashMap<>());
//            } else {
            return map;
//            }

        } else {
            this.userResource.put(userId, new HashMap<>());
            return this.userResource.get(userId);
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
        List<Group> groupList = userService.listUserGroupByUserId(user.getUserId());
        map.clear();
        map.put("resourceList", resources);
        map.put("groupList", groupList);
    }

    public void reloadResources(Integer userId) {
        HashMap<String, Object> map = this.getResourceByUserId(userId);
        List<Resource> resources = userService.listResourceByUserId(userId);
        List<Group> groupList = userService.listUserGroupByUserId(userId);
        map.put("resourceList", resources);
        map.put("groupList", groupList);
    }

    public void clearResources(User user) {
        HashMap<String, Object> map = this.getResourceByUserId(user.getUserId());
        map.clear();
    }
}
