package zx.learn.rbac_demo.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Service;
import zx.learn.rbac_demo.entity.Group;
import zx.learn.rbac_demo.entity.Resource;
import zx.learn.rbac_demo.entity.Role;
import zx.learn.rbac_demo.entity.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/6
 * Time: 8:16
 * Description: 用于处理与User有关的服务
 */


public interface UserService {

    /**
     * 通过用户名和密码 查询符合条件的用户数量
     *
     * @param userName 用户名
     * @param password hash加密后的密码
     * @return 符合条件的用户数量 一般除了 1 就是 0
     */
    public int countUserByUserNameAndPassword(String userName, String password);

    /**
     * 通过用户Id获取用户信息，不包含密码
     *
     * @param userName 用户名
     * @return 没有密码的用户信息
     */
    public User getUserInfoByUserName(String userName);

    /**
     * 修改用户信息，需要判断用户的id和当前用户是否一致，
     * 这个判断是做到业务逻辑里面还是做到权限控制里面？？
     *
     * @param user 特殊处理，将密码置空
     * @return 是否成功
     */
    public Boolean updateUserInfo(User user);

    /**
     * 修改密码
     *
     * @param newPassword 新密码
     * @return 是否成功
     */
    public Boolean updateUserPassword(Integer userId, String newPassword);


    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 是否成功
     */
    public Boolean registerUser(User user);

    /**
     * 禁用用户
     * 问题 如果原先就是禁用状态怎么办，是提示成功还是怎么弄？？？
     *
     * @param userId 要禁用的用户的ID
     * @return 是否禁用成功
     */
    public Boolean banUser(Integer userId);


    /**
     * 删除用户
     * 要求，用户不能是系统预定义的用户，admin
     *
     * @param userId 要删除的用户的ID
     * @return 是否删除成功
     */
    public Boolean deleteUser(Integer userId);


    /**
     * 查询所用的用户的信息，需要能够根据哪些因素查询呢，除了密码，其余所有字段进行模糊搜索。
     *
     * @return 不含密码的用户的信息
     */
    public List<User> listAllUser(User user);

    public List<Role> listUserRoleByUserId(Integer userId);

    public List<Resource> listResourceByUserId(Integer userId);


    List<Group> listUserGroupByUserId(Integer userId);
}
