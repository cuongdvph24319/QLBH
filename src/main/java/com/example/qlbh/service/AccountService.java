package com.example.qlbh.service;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountDTO2;
import com.example.qlbh.model.AccountRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface AccountService {
//    Page<AccountDTO> getAll(Pageable pageable);

    Account create(AccountRequest accountRequest);

    Account update(String ma, AccountRequest accountRequest);

    void delete(Account account);

    Account findAccountByMa(String ma);

    Account findById(Integer id);

    boolean existsByMa(String ma);

    boolean existsById(Integer id);

    boolean existsByIdR(Integer id);

    Page<Account> getAll(String ma, String ten, String hoten, Pageable pageable);

    //    void saveExcelData(Account account);
    Object saveExcelData() throws IOException;

//    boolean hasExcelFormat(MultipartFile file);


//    Page<AccountDTO> getByAccountDTO(String maAccount, String tenAccount, String tenNQH, Pageable pageable);
}
