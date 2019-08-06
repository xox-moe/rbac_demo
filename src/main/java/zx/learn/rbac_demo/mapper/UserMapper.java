package zx.learn.rbac_demo.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;
import zx.learn.rbac_demo.entity.Resource;
import zx.learn.rbac_demo.entity.Role;
import zx.learn.rbac_demo.entity.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/6
 * Time: 14:25
 * Description:
 */
@Mapper
public interface UserMapper {

    @Select(value = "select count(*) from user where user_name = #{userName} and user_password = #{password} ")
    Integer countUserByUserNameAndPassword(String userName, String password);

    @Select(value = "select * from user where user_id = #{userId};")
    User getUserInfoByUserId(Integer userId);

    Boolean updateUserInfo(User user);

    Boolean addUser(User user);

    @Select(value = "select user_id, user_name, user_email, user_phone from user ;")
    List<User> listAllUser(User user);

    List<Role> listUserRoleByUserId(Integer userId);

    List<Resource> listResourceByUserId(Integer userId);

    @Select(value = "select user_id, user_name, user_email, user_phone from user where user_name = #{userName};")
    User getUserInfoByUserName(String userName);
}
