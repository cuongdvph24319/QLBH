package com.example.qlbh.service;

import com.example.qlbh.entity.Account;
import com.example.qlbh.entity.Role;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountRequest;
import com.example.qlbh.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

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

    public Account create(AccountRequest accountRequest) {
        Account account = new Account();
        account.setMa(accountRequest.getMa());
        account.setTen(accountRequest.getTen());
        account.setEmail(accountRequest.getEmail());
        account.setMatKhau(accountRequest.getMatKhau());
        account.setRole(Role.builder().id(accountRequest.getId()).build());
        return accountRepository.save(account);
    }

    public Account update(String ma, AccountRequest accountRequest) {
        Account account = accountRepository.findAccountByMa(ma);

        account.setTen(accountRequest.getTen());
        account.setMatKhau(accountRequest.getMatKhau());
        account.setEmail(accountRequest.getEmail());
        account.setRole(Role.builder().id(accountRequest.getId()).build());

        return accountRepository.save(account);
    }

}
