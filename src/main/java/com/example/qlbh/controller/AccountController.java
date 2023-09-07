package com.example.qlbh.controller;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountRequest;
import com.example.qlbh.repository.AccountRepository;
import com.example.qlbh.service.AccountService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Resource(name = "accountService")
    AccountService accountService;

    @GetMapping(value = "/index", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> index(
            @RequestParam(value = "ma", required = false) String ma,
            @RequestParam(value = "ten", required = false) String tenAc,
            @RequestParam(value = "tenNQH", required = false) String tenNQH,
            @Parameter(hidden = true) Pageable pageable
    ) {
        Page<Account> res = accountService.getAll(ma, tenAc, tenNQH, pageable);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PostMapping("create")
    public ResponseEntity<AccountRequest> create(
            @RequestBody @Valid AccountRequest accountRequest
    ) {
        if (accountService.existsByMa(accountRequest.getMa()) || !accountService.existsByIdR(accountRequest.getId())) {
            return ResponseEntity.badRequest().build();
        }

        Account account = accountService.create(accountRequest);
        accountRequest = new AccountRequest(account);
        return ResponseEntity.ok(accountRequest);
    }

    @PostMapping("upload")
    public ResponseEntity<?> upload(
            MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(accountService.saveExcelData(file));
    }

    @PutMapping("update/{ma}")
    public ResponseEntity<AccountRequest> update(
            @RequestBody @Valid AccountRequest accountRequest,
            @PathVariable("ma") String ma
    ) {
        Account account = accountService.findAccountByMa(ma);
        if (account == null || !accountService.existsByIdR(accountRequest.getId())) {
            return ResponseEntity.notFound().build();
        }
        account = accountService.update(ma, accountRequest);
        accountRequest = new AccountRequest(account);
        return ResponseEntity.ok(accountRequest);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Integer id
    ) {
        Account account = accountService.findById(id);

        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        accountService.delete(account);
        return ResponseEntity.ok().build();
    }


}
