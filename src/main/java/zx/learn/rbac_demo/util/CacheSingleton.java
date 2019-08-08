package zx.learn.rbac_demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
 * Description: 本项目放置缓存的地方，
 */

@Component
@Slf4j
//@Scope(value = )
public class CacheSingleton {

    @Autowired
    UserService userService;

    /**
     * 保存本单例的实例引用
     */
    public static CacheSingleton cacheSingleton;

    /**
     * 用户资源 用于缓存用户的URL权限，
     * Integer 用户ID
     * HashMap<String, Object> 一个资源列表
     * 通常 String 放置资源名称 Object 放置资源
     * 例如 resourceList       List<Resource>
     *     资源名称             实例类型
     *
     */
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

    /**
     * 对指定的用户的权限进行重新加载
     * @param userId 指定的用户的ID
     */
    public void reloadResources(Integer userId) {
        HashMap<String, Object> map = this.getResourceByUserId(userId);
        List<Resource> resources = userService.listResourceByUserId(userId);
        List<Group> groupList = userService.listUserGroupByUserId(userId);
        map.put("resourceList", resources);
        map.put("groupList", groupList);
    }

    /**
     * 对指定用户的资源进行清理
     * @param user
     */
    public void clearResources(User user) {
        HashMap<String, Object> map = this.getResourceByUserId(user.getUserId());
        map.clear();
    }
}
