package com.example.qlbh.repository.repositoryImpl;

import com.example.qlbh.entity.Product;
import com.example.qlbh.model.ProductDTO;
import com.example.qlbh.repository.ProductRepositoty;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductCustomer {

    @Autowired
    ProductRepositoty productRepositoty;

    @Override
    public Page<Product> getAllByProductDTO(String masp, String tensp, String loai, Pageable pageable) {
//        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

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
