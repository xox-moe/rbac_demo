package zx.learn.rbac_demo.test_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zx.learn.rbac_demo.model.Message;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/22
 * Time: 16:04
 * Description:
 */

@Configuration
public class MessageConfig {

    @Bean(name = "message1")
    public Message message() {
        Message m =  new Message();
        m.setUserId(0);
        m.setTitle("title");
        m.setCreateTime(LocalDateTime.now());
        return m;
    }

}
