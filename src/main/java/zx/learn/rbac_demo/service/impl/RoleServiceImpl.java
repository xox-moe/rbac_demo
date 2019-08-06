package zx.learn.rbac_demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zx.learn.rbac_demo.entity.Resource;
import zx.learn.rbac_demo.entity.Role;
import zx.learn.rbac_demo.mapper.RoleMapper;
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
    public Role addRole(Role role) {
        role.setRoleId(null);
        return mapper.addRole(role);
    }

    @Override
    @Transactional
    public Boolean deleteRole(Role role) {

        int userUseCount = mapper.userUseCount(role.getRoleId());
        if (userUseCount > 0) {
            return false;
        } else {
            //注意这里删除的时候 要将该角色拥有的权限一起删除
            mapper.deleteRoleResources(role.getRoleId());
            return mapper.deleteRole(role.getRoleId());
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

}
