package com.example.qlbh.repository;

import com.example.qlbh.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoty extends JpaRepository<Product, Integer> {
    Page<Product> findAll(Specification<Product> specification, Pageable pageable);
}
