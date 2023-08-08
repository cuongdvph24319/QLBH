package com.example.qlbh.service;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountRequest;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAll(Integer pageNo, Integer sise);

    Account create(AccountRequest accountRequest);

    Account update(String ma, AccountRequest accountRequest);
}
