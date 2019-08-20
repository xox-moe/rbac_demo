package zx.learn.rbac_demo.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/20
 * Time: 15:21
 * Description:
 */
@Data
public class Record {

    Integer id;
    Integer formId;
    Integer toId;
    Double amount;
    LocalDateTime time;
    Boolean ifShow;
}
