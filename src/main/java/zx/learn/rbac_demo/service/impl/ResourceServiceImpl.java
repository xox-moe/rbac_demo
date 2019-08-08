package zx.learn.rbac_demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zx.learn.rbac_demo.entity.Resource;
import zx.learn.rbac_demo.mapper.ResourceMapper;
import zx.learn.rbac_demo.service.ResourceService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/6
 * Time: 11:10
 * Description:
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    ResourceMapper mapper;

    @Override
    public Boolean addResource(Resource resource) {
        resource.setResourceId(null);
        return mapper.addResource(resource);
    }

    @Override
    public Boolean updateResource(Resource resource) {
        return mapper.updateResource(resource);
    }

    @Override
    public Boolean deleteResource(Integer resourceId) {
        return  mapper.deleteResource(resourceId);
    }

    @Override
    public List<Resource> listAllResource() {
        return mapper.listAllResource();
    }

    @Override
    public List<Resource> listResourceForRole(Integer roleId) {
        return mapper.listResourceForRole(roleId);
    }

    @Override
    @Transactional
    public void addResourceToRole(Integer roleId, List<Integer> resourceIdList) {
        mapper.deleteAllResourceByRoleId(roleId);
        mapper.addResourceToRole(roleId,resourceIdList);

    }
}
