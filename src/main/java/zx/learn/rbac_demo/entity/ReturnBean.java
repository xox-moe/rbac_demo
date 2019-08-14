package zx.learn.rbac_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/6
 * Time: 16:10
 * Description:
 */
@Data
@AllArgsConstructor
@ToString
public class ReturnBean {

    public int status;
    public int total;
    public String msg;
    public Object data;

    public static ReturnBean getSuccess(String message, Object data, int count) {
        return new ReturnBean(1, count, message, data);
    }

    public static ReturnBean getSuccess(String message, Object data, long count) {
        return new ReturnBean(1, (int) count, message, data);
    }

    public static ReturnBean getSuccess(String message) {
        return new ReturnBean(1, 0, message, null);
    }


    public static ReturnBean getFailed(String message) {
        return new ReturnBean(0, 0, message, null);
    }

}
