package com.example.qlbh.entity;

import jakarta.persistence.*;
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
    private String masp;

    @Column(name = "tensp")
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
