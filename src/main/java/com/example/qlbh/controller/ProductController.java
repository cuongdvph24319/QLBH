package com.example.qlbh.controller;

import com.example.qlbh.entity.Product;
import com.example.qlbh.model.SearchDTO;
import com.example.qlbh.repository.ProductRepositoty;
import com.example.qlbh.repository.repositoryImpl.ProductCustome;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class ProductController {
    @Autowired
    ProductRepositoty productRepositoty;

    @Autowired
    ProductCustome productCustome;

    @GetMapping(value = "/product/index", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getByProduct(
            @RequestParam(value = "masp", required = false) String masp,
            @RequestParam(value = "tensp", required = false) String tensp,
            @RequestParam(value = "loai", required = false) String loai,
            @Parameter(hidden = true) Pageable pageable

    ) {
        SearchDTO searchCriterionDTO = null;
        if (masp != null || tensp != null|| loai != null) {
            searchCriterionDTO = new SearchDTO(masp, tensp, loai);
        }
        Page<Product> resp = productCustome.getAllProduct(searchCriterionDTO, pageable);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
