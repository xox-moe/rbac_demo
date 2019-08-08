package zx.learn.rbac_demo.service;

import zx.learn.rbac_demo.entity.Resource;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/6
 * Time: 8:16
 * Description:
 */

public interface ResourceService {

    public Boolean addResource(Resource resource);

    public Boolean updateResource(Resource resource);

    public Boolean deleteResource(Integer resourceId);

    public List<Resource> listAllResource();


    List<Resource> listResourceForRole(Integer roleId);

    void addResourceToRole(Integer roleId, List<Integer> resourceIdList);
}
