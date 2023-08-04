package com.example.qlbh.controller;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.repository.AccountRepository;
import com.example.qlbh.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/index")
    public ResponseEntity<?> index(
            @Parameter(hidden = true) Pageable pageable
    ) {
        Page<Account> res = accountRepository.findAll(pageable);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Account> create(
            @RequestBody @Valid AccountDTO accountDTO
    ) {
        if (accountRepository.existsByMa(accountDTO.getMa()) || !roleRepository.existsById(accountDTO.getRole().getId())) {
            return ResponseEntity.badRequest().build();
        }
        Account account = new Account();
        account.loadAccountDTOC(accountDTO);
        accountRepository.save(account);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update/{ma}")
    public ResponseEntity<?> update(
            @RequestBody AccountDTO accountDTO,
            @PathVariable("ma") String ma
    ) {
        Account account = accountRepository.findAccountByMa(ma);
        if (account == null || !roleRepository.existsById(accountDTO.getRole().getId())) {
            return ResponseEntity.notFound().build();
        }
        account.loadAccountDTOU(accountDTO);
        accountRepository.save(account);
        return ResponseEntity.ok().build();
    }
}
