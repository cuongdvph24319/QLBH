package com.example.qlbh.controller;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountRequest;
import com.example.qlbh.repository.AccountRepository;
import com.example.qlbh.repository.RelationRepository;
import com.example.qlbh.service.Impl.AccountServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RelationRepository relationRepository;


    @GetMapping(value = "/index", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> index(
            @RequestParam(value = "ma", required = false) String ma,
            @RequestParam(value = "ten", required = false) String tenAc,
            @RequestParam(value = "tenNQH", required = false) String tenNQH,
            @Parameter(hidden = true) Pageable pageable
    ) {
        Page<Account> res = accountRepository.getALl(ma, tenAc, tenNQH, pageable);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Account> create(
            @RequestBody @Valid AccountRequest accountRequest
    ) {
        if (accountRepository.existsByMa(accountRequest.getMa()) || !relationRepository.existsById(accountRequest.getId())) {
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
        if (account == null || !relationRepository.existsById(accountRequest.getId())) {
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
