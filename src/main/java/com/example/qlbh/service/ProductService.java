package com.example.qlbh.service;

import com.example.qlbh.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> getAllByProductDTO(String masp, String tensp, String loai, Pageable pageable);
}
