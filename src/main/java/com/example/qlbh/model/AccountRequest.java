package com.example.qlbh.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountRequest {

    @NotBlank(message = "Không để trống mã")
    private String ma;

    @NotBlank(message = "Không để trống tên")
    private String ten;

    @NotBlank(message = "Không để trống mật khẩu")
    private String matKhau;

    @NotBlank(message = "Không để trống email")
    private String email;

    private Integer id;
}
