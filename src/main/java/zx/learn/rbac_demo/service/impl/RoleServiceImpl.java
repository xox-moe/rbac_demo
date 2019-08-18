package zx.learn.rbac_demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zx.learn.rbac_demo.model.Resource;
import zx.learn.rbac_demo.model.Role;
import zx.learn.rbac_demo.dao.RoleMapper;
import zx.learn.rbac_demo.service.RoleService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/6
 * Time: 11:09
 * Description:
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper mapper;

    @Override
    public Boolean banRole(Integer roleId) {
        return null;
    }

    @Override
    public Role getRoleByRoleId(Integer roleId) {

        return mapper.getRoleByRoleId(roleId);
    }

    @Override
    public void addRole(Role role) {
        role.setRoleId(null);
        mapper.addRole(role);
    }

    @Override
    @Transactional
    public void deleteRole(Integer roleId) throws Exception {

        int userUseCount = mapper.userUseCount(roleId);
        if (userUseCount > 0) {
            throw new Exception("此角色还有用户在使用，无法删除");
        } else {
            //注意这里删除的时候 要将该角色拥有的权限一起删除
            mapper.deleteRoleResources(roleId);
            mapper.deleteRole(roleId);
        }
    }

    @Override
    public Role updateRole(Integer roleId, String roleName) {
        Role role = new Role();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        return mapper.updateRole(role);
    }

    @Override
    public List<Role> listAllRole() {
        return mapper.listAllRole();
    }

    @Override
    public List<Resource> listResourceByRoleId(Integer roleId) {
        return mapper.listResourceByRoleId(roleId);
    }

    @Override
    @Transactional
    public void addRoleToUser(Integer userId, List<Integer> roleIdList) {
        mapper.deleteUserRole(userId);
        if (roleIdList.size() > 0)
            mapper.addRoleToUser(userId, roleIdList);
    }

}
