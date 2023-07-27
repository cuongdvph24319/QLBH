package com.example.qlbh.controller;

import com.example.qlbh.entity.Product;
import com.example.qlbh.repository.ProductRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class ProductController {
    @Autowired
    ProductRepositoty productRepositoty;

    @GetMapping("/product/index")
    public ResponseEntity<Page<Product>> getAll(
            Pageable pageable,
            @RequestBody Product product
    ) {
        return ResponseEntity.ok(productRepositoty.findAll(pageable));
    }
    @GetMapping("/product/index/{key}")
    public ResponseEntity<Page<Product>> getByTen(
            Pageable pageable,
            @PathVariable("key") String key
    ) {
        return ResponseEntity.ok(productRepositoty.getByKey(key , pageable));
    }

//    @GetMapping("/product/index/{masp}")
//    public ResponseEntity<Page<Product>> getByMa(
//            Pageable pageable,
//            @PathVariable("masp") String masp
//    ) {
//        return ResponseEntity.ok(productRepositoty.getByMaSP(masp, pageable));
//    }

}
