package com.example.qlbh.service;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountDTO2;
import com.example.qlbh.model.AccountRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface AccountService {
//    Page<AccountDTO> getAll(Pageable pageable);

    Account create(AccountRequest accountRequest);

    Account update(String ma, AccountRequest accountRequest);

//    Page<AccountDTO> getByAccountDTO(String maAccount, String tenAccount, String tenNQH, Pageable pageable);
}
