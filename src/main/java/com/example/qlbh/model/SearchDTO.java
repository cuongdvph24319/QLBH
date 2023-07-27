package com.example.qlbh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchDTO {

    private String masp;

    private String tensp;

    private String loai;
}
