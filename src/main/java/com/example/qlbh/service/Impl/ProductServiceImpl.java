package com.example.qlbh.service.Impl;

import com.example.qlbh.entity.Product;
import com.example.qlbh.repository.ProductRepositoty;
import com.example.qlbh.service.ProductService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepositoty productRepositoty;

    @Override
    public Page<Product> getAllByProductDTO(String masp, String tensp, String loai, Pageable pageable) {
        Specification<Product> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (masp != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("masp"), masp
                ));
            }
            if (tensp != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("tensp"), tensp
                ));
            }
            if (loai != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("loai"), loai
                ));
            }

            return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        };

        Page<Product> products = productRepositoty.findAll(specification, pageable);
        return products;
    }
}
