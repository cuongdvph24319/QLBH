package com.example.qlbh.repository;

import com.example.qlbh.entity.Product;
import com.example.qlbh.repository.repositoryImpl.ProductCustome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public interface ProductRepositoty extends JpaRepository<Product, Integer> {
    @Query(value = "select * from \"Product\" p where p.tensp = ?1", nativeQuery = true)
    Page<Product> getByTenSP(String tensp, Pageable pageable);

    @Query(value = "SELECT id, masp, tensp, gia, \"khoiLuong\", \"chieuCao\", \"chieuDai\", \"chieuRong\", loai, \"trangThai\"\n" +
            "\tFROM public.\"Product\" p WHERE p.masp = ?1 or p.tensp like ?1 or p.loai = ?1", nativeQuery = true)
    Page<Product> getByKey(String key, Pageable pageable);
}
