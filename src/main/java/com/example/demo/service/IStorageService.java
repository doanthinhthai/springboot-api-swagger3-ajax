package com.example.demo.service;

import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    void init(); // Khởi tạo thư mục
    void store(MultipartFile file, String storeFilename); // Lưu file
    Resource loadAsResource(String filename); // Đọc file thành Resource
    Path load(String filename);
    void delete(String storeFilename) throws Exception; // Xóa file
    String getStorageFilename(MultipartFile file, String id); // Tạo tên file theo chuẩn p{id}.ext
}