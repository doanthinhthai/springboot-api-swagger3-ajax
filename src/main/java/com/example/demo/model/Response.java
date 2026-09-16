package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Boolean status;  // true: thành công, false: thất bại
    private String message;  // Thông báo chi tiết
    private Object body;     // Dữ liệu trả về (có thể là List, Object hoặc null)
}