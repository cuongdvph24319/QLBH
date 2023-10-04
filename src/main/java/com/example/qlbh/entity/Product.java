package com.example.qlbh.entity;

import com.example.qlbh.model.ProductDTO;
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

    public Product(ProductDTO productDTO) {
        this.setMasp(productDTO.getMasp());
        this.setTensp(productDTO.getTensp());
        this.setLoai(productDTO.getLoai());
        this.setGia(productDTO.getGia());
        this.setKhoiLuong(productDTO.getKhoiLuong());
        this.setChieuCao(productDTO.getChieuCao());
        this.setChieuDai(productDTO.getChieuDai());
        this.setChieuRong(productDTO.getChieuRong());
        this.setTrangThai(productDTO.getTrangThai());
    }

//    public Product(String masp, String tensp, Double gia, Double khoiLuong, Double chieuCao, Double chieuDai, Double chieuRong, String loai, Integer trangThai) {
//        this.masp = masp;
//        this.tensp = tensp;
//        this.gia = gia;
//        this.khoiLuong = khoiLuong;
//        this.chieuCao = chieuCao;
//        this.chieuDai = chieuDai;
//        this.chieuRong = chieuRong;
//        this.loai = loai;
//        this.trangThai = trangThai;
//    }

    public void loadDTOU(ProductDTO productDTO) {
        this.setTensp(productDTO.getTensp());
        this.setLoai(productDTO.getLoai());
        this.setGia(productDTO.getGia());
        this.setKhoiLuong(productDTO.getKhoiLuong());
        this.setChieuCao(productDTO.getChieuCao());
        this.setChieuDai(productDTO.getChieuDai());
        this.setChieuRong(productDTO.getChieuRong());
        this.setTrangThai(productDTO.getTrangThai());
    }
}
