package zx.learn.rbac_demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import zx.learn.rbac_demo.entity.Resource;
import zx.learn.rbac_demo.entity.Role;
import zx.learn.rbac_demo.entity.User;
import zx.learn.rbac_demo.mapper.UserMapper;
import zx.learn.rbac_demo.service.UserService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/6
 * Time: 11:09
 * Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper mapper;


    @Override
    public int countUserByUserNameAndPassword(String userName, String password) {
        password = DigestUtils.md5DigestAsHex((password + "zx").getBytes());
        return mapper.countUserByUserNameAndPassword(userName, password);
    }

    @Override
    public User getUserInfoByUserName(String userName) {
        User user = mapper.getUserInfoByUserName(userName);
        user.setUserPassword("***");
        return user;
    }

    @Override
    public Boolean updateUserInfo(User user) {
        user.setUserPassword(null);
        return mapper.updateUserInfo(user);
    }

    @Override
    public Boolean updateUserPassword(Integer userId, String newPassword) {
        newPassword = DigestUtils.md5DigestAsHex((newPassword + "zx").getBytes());
        User user = new User();
        user.setUserPassword(newPassword);
        user.setUserId(userId);
        return mapper.updateUserInfo(user);
    }

    @Override
    public Boolean registerUser(User user) {
        user.setUserPassword(DigestUtils.md5DigestAsHex((user.getUserPassword() + "zx").getBytes()));
        return mapper.addUser(user);
    }

    @Override
    public Boolean banUser(Integer userId) {
        return null;
    }

    @Override
    public Boolean deleteUser(Integer userId) {
        return null;
    }

    @Override
    public List<User> listAllUser(User user) {
        return mapper.listAllUser(user);
    }

    @Override
    public List<Role> listUserRoleByUserId(Integer userId) {
        return mapper.listUserRoleByUserId(userId);

    }

    @Override
    public List<Resource> listResourceByUserId(Integer userId) {
        return mapper.listResourceByUserId(userId);
    }
}
