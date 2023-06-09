package com.hzwn.work0318.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        WebMvcConfigurer.super.addViewControllers(registry);

        registry.addViewController("/").setViewName("index");
        registry.addViewController("/publicHeader.html").setViewName("publicHeader");
        registry.addViewController("/booktype.html").setViewName("booktype");
        registry.addViewController("/mail.html").setViewName("mail");
        registry.addViewController("/jwt.html").setViewName("jwt");
    }
}
