package com.example.qlbh.repository.repositoryImpl;

import com.example.qlbh.entity.Product;
import com.example.qlbh.model.SearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCustome {
    Page<Product> getAllProduct(SearchDTO dto, Pageable pageable);
}
