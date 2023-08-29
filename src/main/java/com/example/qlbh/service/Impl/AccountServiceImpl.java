package com.example.qlbh.service.Impl;

import com.example.qlbh.entity.Account;
import com.example.qlbh.entity.Relation;
import com.example.qlbh.excel.ImportError;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountRequest;
import com.example.qlbh.repository.AccountRepository;
import com.example.qlbh.repository.RelationRepository;
import com.example.qlbh.service.AccountService;
import com.example.qlbh.utils.Utils;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service()
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RelationRepository relationRepository;

    @Autowired
    @Qualifier("utils")
    Utils appendLike;

    @Override
    public Page<Account> getAll(String ma, String ten, String hoten, Pageable pageable) {
        return accountRepository.getAll(ma, ten, hoten, pageable);
    }

    @Override
    public List<ImportError> saveExcelData(MultipartFile file) throws IOException {

        Workbook workbook = new XSSFWorkbook(file.getInputStream());

        Sheet sheet = workbook.getSheet("Accounts");

        // định dạng giá trị trong Excel
        DataFormatter formatter = new DataFormatter();

        List<AccountRequest> requestList = new ArrayList<>();
        List<ImportError> errorList = new ArrayList<>();

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        for (int rowIndex = 0; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            if (rowIndex == 0) {
                continue;
            }
            AccountRequest accountRequest = AccountRequest.builder()
                    .id(Integer.valueOf(formatter.formatCellValue(row.getCell(0))))
                    .ma(formatter.formatCellValue(row.getCell(1)))
                    .ten(formatter.formatCellValue(row.getCell(2)))
                    .matKhau(formatter.formatCellValue(row.getCell(3)))
                    .email(formatter.formatCellValue(row.getCell(4)))
                    .build();
            Set<ConstraintViolation<AccountRequest>> validate = validator.validate(accountRequest);
            Account account = accountRepository.findAccountByMa(formatter.formatCellValue(row.getCell(1)));
            boolean check = check(errorList, row, account, formatter);

            if (!validate.isEmpty() || check) {
                for (ConstraintViolation<AccountRequest> violation : validate) {
                    errorList.add(ImportError.builder()
                            .rowNumber(String.valueOf(row.getRowNum()))
                            .error(violation.getMessage())
                            .build());
                }
            } else {
                requestList.add(accountRequest);

            }

        }
        if (!errorList.isEmpty()) {
            return errorList;
        } else {
            List<Account> accounts = requestList.stream().map(Account::new).collect(Collectors.toList());
            accountRepository.saveAll(accounts);
            return Collections.emptyList(); // Trả về danh sách rỗng nếu không có lỗi
        }


    }

    private boolean check(
            List<ImportError> errorList,
            Row row,
            Account account,
            DataFormatter formatter
    ) {
        if (account != null) {
            errorList.add(
                    ImportError.builder()
                            .rowNumber(String.valueOf(row.getRowNum()))
                            .value(formatter.formatCellValue(row.getCell(1)))
                            .error("Mã Account đã được sử dụng")
                            .build()
            );
            return true;
        }
        return false;
    }

    @Override
    public Account create(AccountRequest accountRequest) {
        Account account = new Account(accountRequest);
        return accountRepository.save(account);
    }

    @Override
    public Account update(String ma, AccountRequest accountRequest) {
        Account account = accountRepository.findAccountByMa(ma);
        account.loadAccountRequestU(accountRequest);
        return accountRepository.save(account);
    }

    @Override
    public void delete(Account account) {
        accountRepository.delete(account);
    }

    @Override
    public Account findAccountByMa(String ma) {
        return accountRepository.findAccountByMa(ma);
    }

    @Override
    public Account findById(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsByMa(String ma) {
        return accountRepository.existsByMa(ma);
    }

    @Override
    public boolean existsById(Integer id) {
        return accountRepository.existsById(id);
    }

    @Override
    public boolean existsByIdR(Integer id) {
        return relationRepository.existsById(id);
    }


//    @Override
//    public Page<AccountDTO> getByAccountDTO(String maAccount, String tenAccount, String tenNQH, Pageable pageable) {
//        Specification<AccountDTO> specification = (root, criteriaQuery, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (maAccount != null) {
//                predicates.add(criteriaBuilder.equal(
//                        root.get("ma"), maAccount
//                ));
//            }
//            if (tenAccount != null) {
//                predicates.add(criteriaBuilder.like(
//                        root.get("ten"), appendLike.appendLike(tenAccount)
//                ));
//            }
//            if (tenNQH != null) {
//                predicates.add(criteriaBuilder.like(
//                        root.get("relation").get("hoTen"), appendLike.appendLike(tenNQH)
//                ));
//            }
//            return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
//        };
//
//        Page<Account> accounts = accountRepository.findAll(specification, pageable);
//        List<AccountDTO> accountDTOS = accounts.getContent().stream().map(AccountDTO::new).collect(Collectors.toList());
//        return new PageImpl<>(accountDTOS, pageable, accounts.getTotalElements());
//
//    }

}
