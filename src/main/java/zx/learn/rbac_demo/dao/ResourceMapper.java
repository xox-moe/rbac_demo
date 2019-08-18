package zx.learn.rbac_demo.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zx.learn.rbac_demo.model.Resource;

import java.util.List;

@Mapper
public interface ResourceMapper {
    
    
    Boolean updateResource(Resource resource);

    @Delete(value = "delete from resource where resource_id = #{resourceId} ")
    Boolean deleteResource(Integer resourceId);

    Boolean addResource(Resource resource);

    @Select("select * from resource;")
    List<Resource> listAllResource();

    List<Resource> listResourceForRole(Integer roleId);

    void deleteAllResourceByRoleId(Integer roleId);


    void addResourceToRole(Integer roleId, List<Integer> resourceIdList);
}
