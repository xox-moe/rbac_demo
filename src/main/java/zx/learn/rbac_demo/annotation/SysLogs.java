package zx.learn.rbac_demo.annotation;

import java.lang.annotation.*;

/**
 * @author 紫川
 * @version 2018/8/14/11:12
 * 系统日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogs {

    String name();

    String type();

}
