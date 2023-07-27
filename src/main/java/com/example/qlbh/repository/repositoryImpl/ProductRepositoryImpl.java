package com.example.qlbh.repository.repositoryImpl;

import com.example.qlbh.entity.Product;
import com.example.qlbh.model.SearchDTO;
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
public class ProductRepositoryImpl implements ProductCustome {

    @Autowired
    ProductRepositoty productRepositoty;

    @Override
    public Page<Product> getAllProduct(SearchDTO dto, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        Specification<Product> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dto != null){
                if (dto.getMasp() != null) {
                    predicates.add(criteriaBuilder.equal(
                            root.get("masp"), dto.getMasp()
                    ));
                }
            if (dto.getTensp() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("tensp"), dto.getTensp()
                ));
            }
            if (dto.getLoai() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("loai"), dto.getLoai()
                ));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
    }
    ;

    Page<Product> products = productRepositoty.findAll(specification, pageable);
    List<Product> productList = new ArrayList<>(products.getContent());
        return new PageImpl<Product>(productList,pageable,products.getTotalElements());

}
}
