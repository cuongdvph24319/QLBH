package com.example.qlbh.entity;

import com.example.qlbh.model.ProductDTO;
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

    public void loadDTOC(ProductDTO productDTO) {
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
