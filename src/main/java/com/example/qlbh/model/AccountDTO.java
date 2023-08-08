package com.example.qlbh.model;

import com.example.qlbh.entity.Account;
import com.example.qlbh.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    private String maRole;

    private String tenRole;

    public AccountDTO(Account account) {
        this.setMa(account.getMa());
        this.setTen(account.getTen());
        this.setMatKhau(account.getMatKhau());
        this.setEmail(account.getEmail());
        this.setMaRole(account.getRole().getMaRole());
        this.setTenRole(account.getRole().getTenRole());
    }
}
