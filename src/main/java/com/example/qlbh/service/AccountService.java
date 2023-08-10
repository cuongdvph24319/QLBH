package com.example.qlbh.service;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
//    Page<AccountDTO> getAll(Pageable pageable);

    Account create(AccountRequest accountRequest);

    Account update(String ma, AccountRequest accountRequest);

    Page<AccountDTO> getByAccountDTO(String maAccount, String tenAccount, String tenNQH, Pageable pageable);
}
