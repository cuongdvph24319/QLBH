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
        ProductDTO searchProductDTO = null;
        if (masp != null || tensp != null || loai != null) {
            searchProductDTO = new ProductDTO(masp, tensp, loai);
        }
        Page<Product> resp = productCustome.getAllByProductDTO(searchProductDTO, pageable);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/product/index")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        if (productRepositoty.existsByMasp(product.getMasp())) {
            return ResponseEntity.badRequest().build();
        }
        productRepositoty.save(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/product/index/{masp}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("masp") String masp,
            @RequestBody @Valid Product product) {
        if (!productRepositoty.existsByMasp(masp)) {
            return ResponseEntity.notFound().build();
        }
        product.setId(productRepositoty.getIdBymasp(masp));
        product.setMasp(masp);
        productRepositoty.saveAndFlush(product);

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