package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.demo.service.IStorageService;

@SpringBootApplication
public class SpringbootApiSwaggerAjaxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApiSwaggerAjaxApplication.class, args);
    }

    // Tự động tạo thư mục uploads khi server khởi động
    @Bean
    CommandLineRunner init(IStorageService storageService) {
        return (args -> {
            storageService.init();
        });
    }
}