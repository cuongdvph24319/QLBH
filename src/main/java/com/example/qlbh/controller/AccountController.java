package com.example.qlbh.controller;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountRequest;
import com.example.qlbh.repository.AccountRepository;
import com.example.qlbh.repository.RoleRepository;
import com.example.qlbh.service.AccountService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/index")
    public ResponseEntity<?> index(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "sise", defaultValue = "1") Integer sise
    ) {
        List<AccountDTO> res = accountService.getAll(page, sise);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Account> create(
            @RequestBody @Valid AccountRequest accountRequest
    ) {
        if (accountRepository.existsByMa(accountRequest.getMa()) || !roleRepository.existsById(accountRequest.getId())) {
            return ResponseEntity.badRequest().build();
        }

        accountService.create(accountRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update/{ma}")
    public ResponseEntity<?> update(
            @RequestBody @Valid AccountRequest accountRequest,
            @PathVariable("ma") String ma
    ) {
        Account account = accountRepository.findAccountByMa(ma);
        if (account == null || !roleRepository.existsById(accountRequest.getId())) {
            return ResponseEntity.notFound().build();
        }
        accountService.update(ma, accountRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Integer id
    ) {
        Account account = accountRepository.findById(id).orElse(null);

        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        accountRepository.delete(account);
        return ResponseEntity.ok().build();
    }

}
