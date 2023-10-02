package com.example.qlbh.model;

import com.example.qlbh.entity.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(url = "https://63ed7cefe6ee53bbf596319e.mockapi.io/cuongdvph24319", name = "my-service")
public interface AccountClient {
    @GetMapping("/account")
    ResponseEntity<List<AccountRequest>> getAllAccounts();

//    @GetMapping("/index")
//    ResponseEntity<String> getAllAccounts();

    @PostMapping("/account")
    AccountRequest addAccount(AccountRequest accountRequest);

    @PutMapping("/account/{id}")
    AccountRequest updateAccount(@PathVariable("id") Integer id,  AccountRequest accountRequest);

    @PatchMapping("/account/{id}")
    AccountRequest patch(@PathVariable("id") Integer id, AccountRequest accountRequest);

    @DeleteMapping("/account/{id}")
    AccountRequest deleteAccount(@PathVariable("id") Integer id);
}
