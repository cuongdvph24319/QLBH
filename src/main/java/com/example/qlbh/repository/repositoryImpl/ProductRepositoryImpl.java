package com.example.qlbh.repository.repositoryImpl;

import com.example.qlbh.entity.Product;
import com.example.qlbh.repository.ProductRepositoty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProductRepositoryImpl implements ProductCustome {
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Product> getAllProduct(Product product, Pageable pageable) {
        StringBuilder sql = new StringBuilder("select * from \"Product\" p")
                .append(" where 1 = 1 ");

        if(!product.getTensp().isEmpty()){
            sql.append(" and p.tensp like '%").append(product.getTensp()).append("%'");
        }
        if(!product.getId().toString().isEmpty()){
            sql.append(" and p.masp like '%" + product.getMasp() + "%'");
        }
        if(!product.getTensp().isEmpty()){
            sql.append(" and p.loai like '%" + product.getLoai() + "%'");
        }

        Query query = em.createNativeQuery(sql.toString());

        return  query.getResultList();
//        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }
}
