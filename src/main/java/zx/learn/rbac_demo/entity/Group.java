package zx.learn.rbac_demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/6
 * Time: 10:57
 * Description:
 */
@Data
public class Group {

    Integer groupId;
    String groupName;

}
