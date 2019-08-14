package zx.learn.rbac_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import zx.learn.rbac_demo.aspect.LogAspect;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/14
 * Time: 14:01
 * Description:
 */
@Configuration
public class LogConfig {

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

}
