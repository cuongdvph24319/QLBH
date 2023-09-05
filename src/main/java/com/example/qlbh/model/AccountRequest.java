package com.example.qlbh.model;

import com.example.qlbh.entity.Account;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    private Integer id;

    @NotBlank(message = "Không để trống mã")
    private String ma;

    @NotBlank(message = "Không để trống tên")
    private String ten;

    @NotBlank(message = "Không để trống mật khẩu")
    private String matKhau;

    @NotBlank(message = "Không để trống email")
    private String email;

    public AccountRequest(Account account) {
        this.setMa(account.getMa());
        this.setTen(account.getTen());
        this.setMatKhau(account.getMatKhau());
        this.setEmail(account.getEmail());
        this.setId(account.getRelation().getId());
    }

}
