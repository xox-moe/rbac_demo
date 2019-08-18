package zx.learn.rbac_demo.service;

import zx.learn.rbac_demo.model.Group;

import java.util.List;

public interface GroupService {

    public List<Group> listAllGroup();

    public Boolean addGroup(Group group);

    public Boolean deleteGroup(Integer groupId);


}
