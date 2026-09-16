package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.demo.entity.Category;

public interface ICategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category entity);
    void deleteById(Long id);
    Optional<Category> findByCategoryName(String name);
    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);
}