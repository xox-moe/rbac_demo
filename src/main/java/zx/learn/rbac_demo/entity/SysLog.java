package zx.learn.rbac_demo.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
    LocalDateTime createDate;

    boolean ifSuccess = true;

}
