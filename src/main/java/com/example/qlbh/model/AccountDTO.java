package com.example.qlbh.model;

import com.example.qlbh.entity.Account;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    @NotBlank(message = "Không để trống mã")
    private String ma;

    @NotBlank(message = "Không để trống tên")
    private String ten;

    @NotBlank(message = "Không để trống mật khẩu")
    private String matKhau;

    @NotBlank(message = "Không để trống email")
    private String email;

    private String tenNQH;

    private String hoTen;

    public AccountDTO(Account account) {
        this.setMa(account.getMa());
        this.setTen(account.getTen());
        this.setMatKhau(account.getMatKhau());
        this.setEmail(account.getEmail());
        this.setTenNQH(account.getRelation().getTenNQH());
        this.setHoTen(account.getRelation().getHoTen());
    }
}
