package top.putileaf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.putileaf.interceptors.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private LoginInterceptor loginInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录接口和注册接口不拦截
        registry.addInterceptor(loginInterceptors).excludePathPatterns("/user/login","/user/register","/user/getCode","/user/forgetPwd");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 配置允许所有路径
                .allowedOrigins("http://me.putileaf.top", "http://putileaf.top") // 允许特定的源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH") // 允许的HTTP方法
                .allowedHeaders("*") // 允许所有的头部
                .allowCredentials(true) // 允许携带凭证
                .maxAge(3600); // 预检请求的有效期，单位秒
    }

}
