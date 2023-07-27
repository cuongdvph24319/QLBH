package com.example.qlbh.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "masp")
    private String masp;

    @Column(name = "tensp")
    private String tensp;

    @Column(name = "gia")
    private Double gia;

    @Column(name = "khoiLuong")
    private Double khoiLuong;

    @Column(name = "chieuCao")
    private Double chieuCao;

    @Column(name = "chieuDai")
    private Double chieuDai;

    @Column(name = "chieuRong")
    private Double chieuRong;

    @Column(name = "loai")
    private String loai;

    @Column(name = "trangThai")
    private Integer trangThai;

}
