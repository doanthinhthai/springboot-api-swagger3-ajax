package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebViewController {

    // Mở trang Quản lý Category bằng AJAX
    @GetMapping("/category-ajax")
    public String categoryAjax() {
        return "category_ajax"; // Mở file templates/category_ajax.html
    }

    // Mở trang Quản lý Product bằng AJAX
    @GetMapping("/product-ajax")
    public String productAjax() {
        return "product_ajax"; // Mở file templates/product_ajax.html
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/category-ajax";
    }
}