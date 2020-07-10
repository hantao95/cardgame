package com.ht.card.Config;

import com.ht.card.component.LoginHandlerInterceptor;
import com.ht.card.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * 自定义登录拦截器
     * @return
     */
    @Bean
    public LoginHandlerInterceptor LoginHandlerInterceptor() {
        return new LoginHandlerInterceptor();
    }

    /**
     * 添加路径映射
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("main");
        registry.addViewController("/sockettest1.html").setViewName("sockettest1");
        registry.addViewController("/gamemain.html").setViewName("gamemain");

    }

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截规则
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(LoginHandlerInterceptor());
        //放行路径  此处需要把静态资源的路径写上
        interceptorRegistration.excludePathPatterns("/static/**","/webjars/**","/index.html","/","/hello","/login.do","/register.do");
        //拦截路径
        interceptorRegistration.addPathPatterns("/**");
    }

    /**
     * @param registry 配置静态资源放行
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/i18n/**").addResourceLocations("classpath:/i18n/");
//        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
    }

    /**
     * 国际化
     * @return
     */
    @Bean
    public LocaleResolver localeResolver(){
        return  new MyLocaleResolver();
    }
}
