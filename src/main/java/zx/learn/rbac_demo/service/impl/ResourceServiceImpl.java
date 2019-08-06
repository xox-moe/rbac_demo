package zx.learn.rbac_demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public Resource addResource(Resource resource) {
        resource.setResourceId(null);
        return mapper.addResource(resource);
    }

    @Override
    public Boolean updateResource(Resource resource) {
        return mapper.updateResource(resource);
    }

    @Override
    public Boolean deleteResource(Resource resource) {
        return  mapper.deleteResource(resource.getResourceId());
    }

    @Override
    public List<Resource> listAllResource() {
        return mapper.listAllResource();
    }
}
