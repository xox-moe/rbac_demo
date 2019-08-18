package zx.learn.rbac_demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zx.learn.rbac_demo.model.Group;
import zx.learn.rbac_demo.dao.GroupMapper;
import zx.learn.rbac_demo.service.GroupService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/7
 * Time: 14:13
 * Description:
 */

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupMapper mapper;

    @Override
    public List<Group> listAllGroup() {
        return mapper.listAllGroup();
    }

    @Override
    public Boolean addGroup(Group group) {
        return mapper.addGroup(group);
    }

    @Override
    public Boolean deleteGroup(Integer groupId) {
        return mapper.deleteGroup(groupId);
    }
}
