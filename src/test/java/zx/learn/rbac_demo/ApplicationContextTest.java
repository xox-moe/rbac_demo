package zx.learn.rbac_demo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import zx.learn.rbac_demo.model.Message;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/22
 * Time: 16:02
 * Description:
 */
public class ApplicationContextTest {

    @Test
    public void testApplicationContext() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("zx.learn.rbac_demo.test_config");
        Message message =  ctx.getBean("message1",Message.class);
        System.out.println(message);
    }

}
