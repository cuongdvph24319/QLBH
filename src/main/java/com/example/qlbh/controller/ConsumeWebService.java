package com.example.qlbh.controller;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountClient;
import com.example.qlbh.model.AccountRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restemplate/")
public class ConsumeWebService {
    static String url = "https://63ed7cefe6ee53bbf596319e.mockapi.io/cuongdvph24319/account";

    @Autowired
    RestTemplate restTemplate;

    final AccountClient accountClient;

    @Autowired
    public ConsumeWebService(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<String> getAccountList() {
        return restTemplate.getForEntity(url, String.class);
    }

    @PostMapping("/add")
    public AccountRequest add(@RequestBody AccountRequest accountRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON); //  xác định kiểu gửi lên
        HttpEntity<AccountRequest> entity = new HttpEntity<>(accountRequest, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.POST, entity, AccountRequest.class).getBody();
    }

    @PutMapping("/update/{id}")
    public AccountRequest update(
            @PathVariable("id") Integer id,
            @RequestBody AccountRequest accountRequest
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)); // xác định kiểu trả về
        HttpEntity<AccountRequest> entity = new HttpEntity<>(accountRequest, httpHeaders);
        return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, entity, AccountRequest.class).getBody();
    }

    @DeleteMapping("/delete/{id}")
    public AccountRequest delete(
            @PathVariable("id") Integer id
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML));
        HttpEntity<AccountRequest> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, entity, AccountRequest.class).getBody();

    }

    //==================================================================================
//    , produces = "application/json"
    @GetMapping(value = "getAll") // xác định kiểu phản hồi
    public ResponseEntity<List<AccountRequest>> getAll() {
        ResponseEntity<List<AccountRequest>> responseEntity = accountClient.getAllAccounts();
        List<AccountRequest> accounts = responseEntity.getBody();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("addFeign")
    public AccountRequest addFeign(
            @RequestBody AccountRequest accountRequest
    ) {
        return accountClient.addAccount(accountRequest);
    }

    @PatchMapping("patch/{id}")
    public AccountRequest put(
            @PathVariable("id") Integer id,
            @RequestBody AccountRequest accountRequest
//            @RequestBody Map<String, Object> updates
    ) {
        return accountClient.patch(id, accountRequest);
    }

    @PutMapping("updateFeign/{id}")
    public AccountRequest patch(
            @PathVariable("id") Integer id,
            @RequestBody AccountRequest accountRequest
    ) {
        return accountClient.updateAccount(id, accountRequest);
    }

    @DeleteMapping("deleteFeign/{id}")
    public AccountRequest deleteFeign(
            @PathVariable("id") Integer id
    ) {
        return accountClient.deleteAccount(id);
    }
}
