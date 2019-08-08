package zx.learn.rbac_demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import zx.learn.rbac_demo.entity.Resource;
import zx.learn.rbac_demo.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Select(value = "select count(user_id) from user_role where role_id = #{roleId} ;")
    int userUseCount(Integer roleId);

    @Select(value = "select role_id, role_name from role where role_id = #{roleId} ;")
    Role getRoleByRoleId(Integer roleId);

    Boolean addRole(Role role);

    @Delete(value = "delete from role_resource where role_id = #{roleId} ")
    void deleteRoleResources(@Param("roleId") Integer roleId);

    @Delete(value = "delete from role where role_id = #{roleId} ")
    Boolean deleteRole(Integer roleId);

    Role updateRole(Role role);

    @Select(value = "select role_id, role_name from role ")
    List<Role> listAllRole();


    List<Resource> listResourceByRoleId(Integer roleId);

    void addRoleToUser(Integer userId, List<Integer> roleIdList);

    void deleteUserRole(Integer userId);
}

