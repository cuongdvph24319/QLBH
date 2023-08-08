package com.example.qlbh.service.Impl;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountRequest;
import com.example.qlbh.repository.AccountRepository;
import com.example.qlbh.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAll(Integer pageNo, Integer sise) {
        Pageable pageable = PageRequest.of(pageNo, sise);
        Page<Account> list = accountRepository.findAll(pageable);
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for (Account account : list.getContent()
        ) {
            accountDTOS.add(new AccountDTO(account));
        }
        accountDTOS = list.stream().map(AccountDTO::new).collect(Collectors.toList());
        return accountDTOS;
    }

    @Override
    public Account create(AccountRequest accountRequest) {
        Account account = new Account();
        account.loadAccountRequestC(accountRequest);
        return accountRepository.save(account);
    }

    @Override
    public Account update(String ma, AccountRequest accountRequest) {
        Account account = accountRepository.findAccountByMa(ma);
        account.loadAccountRequestU(accountRequest);
        return accountRepository.save(account);
    }
}
