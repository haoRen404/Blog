package com.lcy.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

// 拦截器的配置文件，配置拦截的路径

@Configuration  // // 标识这个一个配置类
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {// 把拦截的类加入到配置中
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")   // 拦截
                .excludePathPatterns("/admin")  // 排除
                .excludePathPatterns("/admin/login");   // 排除
    }
}
