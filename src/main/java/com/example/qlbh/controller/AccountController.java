package com.example.qlbh.controller;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountRequest;
import com.example.qlbh.model.CustomException;
import com.example.qlbh.repository.AccountRepository;
import com.example.qlbh.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Resource(name = "accountService")
    private AccountService accountService;

    @Resource(name = "accountRepository")
    private AccountRepository accountRepository;


    RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value = "/index", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> index(
            @RequestParam(value = "ma", required = false) String ma,
            @RequestParam(value = "ten", required = false) String tenAc,
            @RequestParam(value = "tenNQH", required = false) String tenNQH,
            @Parameter(hidden = true) Pageable pageable
    ) {
        Page<AccountDTO> res = accountService.getByAccountDTO(ma, tenAc, tenNQH, pageable);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PostMapping("create")
    public ResponseEntity<AccountRequest> create(
            @RequestBody AccountRequest accountRequest
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
        if (account == null) { //|| !accountService.existsByIdR(accountRequest.getId())
            return ResponseEntity.notFound().build();
        }
        account = accountService.update(ma, accountRequest);
        accountRequest = new AccountRequest(account);
        return ResponseEntity.ok(accountRequest);
    }

    @PatchMapping("patch/{ma}")
    public ResponseEntity<?> patch(
            @PathVariable("ma") String ma,
//            @RequestBody AccountRequest accountRequest,
            @RequestBody Map<String, Object> updates
    ) {
        Account account = accountService.findAccountByMa(ma);

        if (account == null) { //|| !accountService.existsByIdR(accountRequest.getId())
            return ResponseEntity.notFound().build();
        }
        updates.forEach((key, value) -> {
            switch (key) {
                case "ten":
                    account.setTen((String) value);
                    break;
                case "email":
                    account.setEmail((String) value);
                    break;
                case "matKhau":
                    account.setMatKhau((String) value);
                    break;
                default:
                    break;
            }
        });
        accountRepository.save(account);
        return ResponseEntity.ok(account);
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

    @GetMapping("json")
    public ResponseEntity<List<AccountRequest>> jon(
//            @RequestBody String json
    ) {
        String url = "https://63ed7cefe6ee53bbf596319e.mockapi.io/cuongdvph24319/account";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        String json = responseEntity.getBody();

        Gson gson = new Gson();

        // Chuyển đổi JSON thành list đối tượng
        List<AccountRequest> accounts = gson.fromJson(json, new TypeToken<List<AccountRequest>>() {
        }.getType());
//        AccountRequest accounts = gson.fromJson(json, AccountRequest.class);
        return ResponseEntity.ok(accounts);
    }
}
