package com.example.qlbh.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "masp")
    @Size(max = 10, message = "Mã sản phẩm không quá 10 kí tự")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Sai định dạng")
    private String masp;

    @Column(name = "tensp")
    @Size(max = 200, message = "Tên sản phẩm không quá 200 kí tự")
    private String tensp;

    @Column(name = "gia")
    private Double gia;

    @Column(name = "khoiluong")
    private Double khoiLuong;

    @Column(name = "chieucao")
    private Double chieuCao;

    @Column(name = "chieudai")
    private Double chieuDai;

    @Column(name = "chieurong")
    private Double chieuRong;

    @Column(name = "loai")
    private String loai;

    @Column(name = "trangthai")
    private Integer trangThai;

}
