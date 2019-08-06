package zx.learn.rbac_demo.service;

import org.springframework.stereotype.Service;
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

    public Resource addResource(Resource resource);

    public Boolean updateResource(Resource resource);

    public Boolean deleteResource(Resource resource);

    public List<Resource> listAllResource();


}
