package zx.learn.rbac_demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import zx.learn.rbac_demo.filter.AccessAllowInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public AccessAllowInterceptor AccessAllowInterceptor() {
        return new AccessAllowInterceptor();
    }

    /**
     * 上传地址
     */
    @Value("${file.upload.path}")
    private String filePath;
    /**
     * 显示相对地址
     */
    @Value("${file.upload.relative}")
    private String fileRelativePath;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
        registry.addInterceptor(AccessAllowInterceptor()).addPathPatterns("/**");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //添加 资源解析器
        registry.addResourceHandler(fileRelativePath).addResourceLocations("file:/" + filePath);
    }
}
