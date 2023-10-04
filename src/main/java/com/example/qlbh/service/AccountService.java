package com.example.qlbh.service;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountRequest;
import com.example.qlbh.model.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface AccountService {
//    Page<AccountDTO> getAll(Pageable pageable);

    Account create(AccountRequest accountRequest) throws CustomException;

    Account update(String ma, AccountRequest accountRequest);

    void delete(Account account);

    Account findAccountByMa(String ma);

    Account findById(Integer id);

    boolean existsByMa(String ma);

    boolean existsById(Integer id);

    boolean existsByIdR(Integer id);

    Page<Account> getAll(String ma, String ten, String hoten, Pageable pageable);

    //    void saveExcelData(Account account);
    Object saveExcelData(MultipartFile file) throws IOException;

//    boolean hasExcelFormat(MultipartFile file);

    Page<AccountDTO> getByAccountDTO(String maAccount, String tenAccount, String tenNQH, Pageable pageable);
}
