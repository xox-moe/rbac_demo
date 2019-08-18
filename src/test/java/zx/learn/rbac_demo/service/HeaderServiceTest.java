package zx.learn.rbac_demo.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class HeaderServiceTest {

    @Autowired
    HeaderService headerService;


    @Test
    public void saveImgTest() {
        String url = "/test/url" + UUID.randomUUID().toString();

        Integer imgId = headerService.saveImage(url);

        assert imgId != null;

        log.info(imgId.toString());

        String urls = headerService.getUrlById(imgId);


        log.info("url: " + url);
        log.info("urls: " + urls);

        assert urls.equals(url);


    }


}
