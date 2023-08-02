package com.example.qlbh.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    @Size(max = 10, message = "Mã sản phẩm không quá 10 kí tự")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Sai định dạng")
    private String masp;

    @Size(max = 10, message = "Tên sản phẩm không quá 200 kí tự")
    private String tensp;

    private String loai;

    private Double gia;

    private Double khoiLuong;

    private Double chieuCao;

    private Double chieuDai;

    private Double chieuRong;

    private Integer trangThai;

}
