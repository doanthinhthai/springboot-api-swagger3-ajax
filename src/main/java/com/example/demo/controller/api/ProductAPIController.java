package com.example.demo.controller.api;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.model.Response;
import com.example.demo.service.IProductService;
import com.example.demo.service.IStorageService;

@RestController
@RequestMapping(path = "/api/product")
public class ProductAPIController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IStorageService storageService;

    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<>(new Response(true, "Thành công", productService.findAll()), HttpStatus.OK);
    }

    @PostMapping(path = "/getProduct")
    public ResponseEntity<?> getProduct(@RequestParam("id") Long id) {
        Optional<Product> optProduct = productService.findById(id);
        if (optProduct.isPresent()) {
            return new ResponseEntity<>(new Response(true, "Thành công", optProduct.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(false, "Không tìm thấy sản phẩm", null), HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/addProduct", consumes = "multipart/form-data")
    public ResponseEntity<?> addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("unitPrice") Double unitPrice,
            @RequestParam("quantity") Integer quantity,
            @RequestParam(value = "description", required = false, defaultValue = "") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        try {
            Product product = new Product();
            product.setProductName(productName);
            product.setUnitPrice(unitPrice);
            product.setQuantity(quantity);
            product.setDescription(description);
            product.setDiscount(0.0);
            product.setStatus((short) 1);
            product.setCreateDate(new Date());

            Category cateEntity = new Category();
            cateEntity.setCategoryId(categoryId);
            product.setCategory(cateEntity);

            if (imageFile != null && !imageFile.isEmpty()) {
                String uuid = UUID.randomUUID().toString();
                String filename = storageService.getStorageFilename(imageFile, uuid);
                storageService.store(imageFile, filename);
                product.setImages(filename);
            }

            Product saved = productService.save(product);
            return new ResponseEntity<>(new Response(true, "Thêm Thành công", saved), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Response(false, "Lỗi thêm: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/updateProduct", consumes = "multipart/form-data")
    public ResponseEntity<?> updateProduct(
            @RequestParam("productId") Long productId,
            @RequestParam("productName") String productName,
            @RequestParam("unitPrice") Double unitPrice,
            @RequestParam("quantity") Integer quantity,
            @RequestParam(value = "description", required = false, defaultValue = "") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        try {
            Optional<Product> optProduct = productService.findById(productId);
            if (optProduct.isEmpty()) {
                return new ResponseEntity<>(new Response(false, "Không tìm thấy sản phẩm", null), HttpStatus.BAD_REQUEST);
            }

            Product product = optProduct.get();
            product.setProductName(productName);
            product.setUnitPrice(unitPrice);
            product.setQuantity(quantity);
            product.setDescription(description);

            Category cateEntity = new Category();
            cateEntity.setCategoryId(categoryId);
            product.setCategory(cateEntity);

            if (imageFile != null && !imageFile.isEmpty()) {
                String uuid = UUID.randomUUID().toString();
                String filename = storageService.getStorageFilename(imageFile, uuid);
                storageService.store(imageFile, filename);
                product.setImages(filename);
            }

            Product updated = productService.save(product);
            return new ResponseEntity<>(new Response(true, "Cập nhật Thành công", updated), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Response(false, "Lỗi cập nhật: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam("productId") Long productId) {
        Optional<Product> optProduct = productService.findById(productId);
        if (optProduct.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy sản phẩm", null), HttpStatus.BAD_REQUEST);
        }
        productService.deleteById(productId);
        return new ResponseEntity<>(new Response(true, "Xóa Thành công", null), HttpStatus.OK);
    }
}