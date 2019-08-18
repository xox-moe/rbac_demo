package zx.learn.rbac_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/6
 * Time: 10:57
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    Integer groupId;
    String groupName;

}
