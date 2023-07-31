package com.example.qlbh.repository.repositoryImpl;

import com.example.qlbh.entity.Product;
import com.example.qlbh.model.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCustomer {
    Page<Product> getAllByProductDTO(ProductDTO dto, Pageable pageable);
}
