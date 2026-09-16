package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.demo.entity.Product;

public interface IProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product entity);
    void deleteById(Long id);
    Optional<Product> findByProductName(String name);
    Page<Product> findByProductNameContaining(String name, Pageable pageable);
}