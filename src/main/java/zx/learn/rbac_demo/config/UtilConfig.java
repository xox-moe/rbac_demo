//package zx.learn.rbac_demo.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import zx.learn.rbac_demo.util.FileUtils;
//
///**
// * Created with IntelliJ IDEA.
// * User: zx
// * Date: 2019/8/19
// * Time: 8:08
// * Description:
// */
//public class UtilConfig {
//
//    @Value("${file.upload.path}")
//    private String filePath;
//
//    @Bean
//    FileUtils fileUtils() {
//        return FileUtils.getInstance(filePath);
//    }
//
//}
