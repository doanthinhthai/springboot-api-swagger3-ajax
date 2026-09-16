package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cho phép truy cập ảnh qua URL: /uploads/** và /admin/categories/images/**
        registry.addResourceHandler("/uploads/**", "/admin/categories/images/**")
                .addResourceLocations("file:uploads/");
    }
}