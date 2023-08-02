package com.example.qlbh.controller;

import com.example.qlbh.entity.Product;
import com.example.qlbh.model.ProductDTO;
import com.example.qlbh.repository.ProductRepositoty;
import com.example.qlbh.repository.repositoryImpl.ProductCustomer;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class ProductController {
    @Autowired
    ProductRepositoty productRepositoty;

    @Autowired
    ProductCustomer productCustome;

    @GetMapping(value = "/product/index", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByProduct(
            @RequestParam(value = "masp", required = false) String masp,
            @RequestParam(value = "tensp", required = false) String tensp,
            @RequestParam(value = "loai", required = false) String loai,
            @Parameter(hidden = true) Pageable pageable

    ) {
        Page<Product> resp = productCustome.getAllByProductDTO(masp, tensp, loai, pageable);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/product/index")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        if (productRepositoty.existsByMasp(productDTO.getMasp())) {
            return ResponseEntity.badRequest().build();
        }
        Product product = new Product();
        product.loadDTOC(productDTO);
        productRepositoty.save(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/product/index/{masp}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("masp") String masp,
            @RequestBody @Valid ProductDTO productDTO) {
        Product product = productRepositoty.findProductByMasp(masp);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        product.loadDTOU(productDTO);
        productRepositoty.save(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/product/index/{id}")
    public ResponseEntity<Product> deleteProduct(
            @PathVariable("id") Integer id
    ) {
        Product product = productRepositoty.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        product.setTrangThai(0);
        productRepositoty.save(product);
        return ResponseEntity.ok().build();
    }
}