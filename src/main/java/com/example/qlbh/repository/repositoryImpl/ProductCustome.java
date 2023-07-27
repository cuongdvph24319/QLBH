package com.example.qlbh.repository.repositoryImpl;

import com.example.qlbh.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCustome {
    List<Product> getAllProduct(Product product, Pageable pageable);

}
