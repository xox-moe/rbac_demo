package zx.learn.rbac_demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/14
 * Time: 16:02
 * Description:
 */

@Data
public class SysLog {

    Integer id;
    String actionName;
    String actionType;
    String methodName;
    String args;
    String ip;
    String url;
    Integer userId;
    String userName;
    long timeUse;
    String returnResult;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createDate;

    boolean ifSuccess = true;

}
