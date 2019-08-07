package zx.learn.rbac_demo.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zx.learn.rbac_demo.entity.Group;

import java.util.List;

@Mapper
public interface GroupMapper {


    @Select(" select group_id, group_name from `group`")
    List<Group> listAllGroup();

    @Insert("insert into `group` ( group_name) values (#{groupName}  );")
    Boolean addGroup(Group group);

    @Delete("delete from `group` where group_id = #{groupId} ")
    Boolean deleteGroup(Integer groupId);
}
