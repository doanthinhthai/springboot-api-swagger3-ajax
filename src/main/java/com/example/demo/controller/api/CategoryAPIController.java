package com.example.demo.controller.api;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Category;
import com.example.demo.model.Response;
import com.example.demo.service.ICategoryService;
import com.example.demo.service.IStorageService;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryAPIController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IStorageService storageService;

    // Lấy toàn bộ danh sách Category (GET /api/category)
    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        return new ResponseEntity<>(new Response(true, "Thành công", categoryService.findAll()), HttpStatus.OK);
    }

    // Lấy chi tiết 1 Category (POST /api/category/getCategory)
    @PostMapping(path = "/getCategory")
    public ResponseEntity<?> getCategory(@RequestParam("id") Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(new Response(true, "Thành công", category.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(false, "Thất bại", null), HttpStatus.NOT_FOUND);
        }
    }

    // Thêm mới Category có upload icon (POST /api/category/addCategory)
    @PostMapping(path = "/addCategory", consumes = "multipart/form-data")
    public ResponseEntity<?> addCategory(
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {

        Optional<Category> optCategory = categoryService.findByCategoryName(categoryName);
        if (optCategory.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Category đã tồn tại trong hệ thống", null));
        }

        Category category = new Category();
        category.setCategoryName(categoryName);

        // Kiểm tra và lưu file icon
        if (icon != null && !icon.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String filename = storageService.getStorageFilename(icon, uuid);
            storageService.store(icon, filename);
            category.setIcon(filename);
        }

        categoryService.save(category);
        return new ResponseEntity<>(new Response(true, "Thêm Thành công", category), HttpStatus.OK);
    }

    // Cập nhật Category (PUT /api/category/updateCategory)
    @PutMapping(path = "/updateCategory", consumes = "multipart/form-data")
    public ResponseEntity<?> updateCategory(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {

        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        }

        Category category = optCategory.get();
        category.setCategoryName(categoryName);

        if (icon != null && !icon.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String filename = storageService.getStorageFilename(icon, uuid);
            storageService.store(icon, filename);
            category.setIcon(filename);
        }

        categoryService.save(category);
        return new ResponseEntity<>(new Response(true, "Cập nhật Thành công", category), HttpStatus.OK);
    }

    // Xóa Category (DELETE /api/category/deleteCategory)
    @DeleteMapping(path = "/deleteCategory")
    public ResponseEntity<?> deleteCategory(@RequestParam("categoryId") Long categoryId) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        }
        categoryService.deleteById(categoryId);
        return new ResponseEntity<>(new Response(true, "Xóa Thành công", null), HttpStatus.OK);
    }
}