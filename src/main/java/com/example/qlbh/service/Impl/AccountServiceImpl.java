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
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
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
    public Object saveExcelData(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        CellReference cellRef;
        Sheet sheet = workbook.getSheet("Accounts");

        // định dạng giá trị trong Excel
        DataFormatter formatter = new DataFormatter();

        List<AccountRequest> requestList = new ArrayList<>();
        List<ImportError> errorList = new ArrayList<>();
        List<String> listMa = accountRepository.getAllMa();
        List<Integer> listRelation = relationRepository.getAllId();
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        Validator validator = validatorFactory.getValidator();

        for (int rowIndex = 0; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            if (rowIndex == 0 || !rowHasData(row)) {
                continue;
            }

            Cell re_idCell = row.getCell(0);
            Cell maCell = row.getCell(1);
            Cell tenCell = row.getCell(2);
            Cell matKhauCell = row.getCell(3);
            Cell emailCell = row.getCell(4);

            Integer re_id = null;
            try {
                re_id = Integer.valueOf(formatter.formatCellValue(re_idCell));
            } catch (NumberFormatException ex) {
                // check id rong
                if (re_idCell != null) {
                    String re_idC = re_idCell.getStringCellValue().trim();
                    if (re_idC.isEmpty()) {
                        cellRef = new CellReference(row.getRowNum(), 0);
                        addErrorIfNotEmpty(errorList, row, "relation_id", cellRef, re_idCell, formatter, "Không để trống relation_id");
                    }
                } else {
                    cellRef = new CellReference(row.getRowNum(), 0);
                    addErrorIfEmpty(errorList, row, "relation_id", formatter.formatCellValue(re_idCell), re_idCell, formatter, "Không để trống relation_id", cellRef.formatAsString());
                }
            }
            String ma = formatter.formatCellValue(maCell);
            String ten = formatter.formatCellValue(tenCell);
            String matKhau = formatter.formatCellValue(matKhauCell);
            String email = formatter.formatCellValue(emailCell);

            AccountRequest accountRequest = new AccountRequest(
                    re_id,
                    ma,
                    ten,
                    matKhau,
                    email
            );


            // check ma
            if (maCell != null) {
                String maC = maCell.getStringCellValue().trim();
                if (maC.isEmpty()) {
                    cellRef = new CellReference(row.getRowNum(), 1);
                    addErrorIfNotEmpty(errorList, row, "ma", cellRef, maCell, formatter, "Không để trống mã");
                }

            } else {
                cellRef = new CellReference(row.getRowNum(), 1);
                addErrorIfEmpty(errorList, row, "ma", ma, maCell, formatter, "Không để trống mã", cellRef.formatAsString());
            }
            // check ten
            if (tenCell != null) {
                String tenC = tenCell.getStringCellValue().trim();
                if (tenC.isEmpty()) {
                    cellRef = new CellReference(row.getRowNum(), 2);
                    addErrorIfNotEmpty(errorList, row, "ten", cellRef, maCell, formatter, "Không để trống tên");
                }

            } else {
                cellRef = new CellReference(row.getRowNum(), 2);
                addErrorIfEmpty(errorList, row, "ten", ten, tenCell, formatter, "Không để trống tên", cellRef.formatAsString());
            }
            // check mat khau
            if (matKhauCell != null) {
                String matKhauC = matKhauCell.getStringCellValue().trim();
                if (matKhauC.isEmpty()) {
                    cellRef = new CellReference(row.getRowNum(), 3);
                    addErrorIfNotEmpty(errorList, row, "mat khau", cellRef, maCell, formatter, "Không để trống mật khẩu");
                }

            } else {
                cellRef = new CellReference(row.getRowNum(), 3);
                addErrorIfEmpty(errorList, row, "mat khau", matKhau, matKhauCell, formatter, "Không để trống mật khẩu", cellRef.formatAsString());
            }
            // check email
            if (emailCell != null) {
                String emailC = emailCell.getStringCellValue().trim();
                if (emailC.isEmpty()) {
                    cellRef = new CellReference(row.getRowNum(), 4);
                    addErrorIfEmpty(errorList, row, "email", matKhau, matKhauCell, formatter, "Không để trống email", cellRef.formatAsString());
                }

            }

//            Set<ConstraintViolation<AccountRequest>> validate = validator.validate(accountRequest);
            // check ton tai relation_id
            if (!listRelation.contains(re_id)) {
                cellRef = new CellReference(row.getRowNum(), 0);
                addErrorIfNotEmpty(errorList, row, "relation_id", cellRef, re_idCell, formatter, "Không tồn tại relation_id này");
            }            // check trung ma
            if (listMa.contains(ma)) {
                cellRef = new CellReference(row.getRowNum(), 1);
                addErrorIfNotEmpty(errorList, row, "ma", cellRef, maCell, formatter, "Mã Account đã được sử dụng");
            }
            requestList.add(accountRequest);

        }
        if (!errorList.isEmpty()) {
            return errorList;
        } else {
            List<Account> accounts = requestList.stream().map(Account::new).collect(Collectors.toList());
            return accountRepository.saveAll(accounts);
        }


    }

    private void addErrorIfNotEmpty(
            List<ImportError> errorList,
            Row row,
            String column,
            CellReference cellRef,
            Cell cell,
            DataFormatter formatter,
            String errorMessage
    ) {
        if (cell != null && !formatter.formatCellValue(cell).trim().isEmpty()) {
            ImportError importError = new ImportError(
                    String.valueOf(row.getRowNum() + 1),
                    column,
                    cellRef.formatAsString(),
                    formatter.formatCellValue(cell),
                    errorMessage
            );
            errorList.add(importError);
        }
    }

    private void addErrorIfEmpty(
            List<ImportError> errorList,
            Row row,
            String column,
            String value,
            Cell cell,
            DataFormatter formatter,
            String errorMessage,
            String cells
    ) {
        if (value.isEmpty()) {
            ImportError importError = new ImportError(
                    String.valueOf(row.getRowNum() + 1),
                    column,
                    cells,
                    formatter.formatCellValue(cell),
                    errorMessage
            );
            errorList.add(importError);
        }
    }


    private boolean rowHasData(Row row) {
        if (row == null) {
            return false; // Dòng không tồn tại, không có dữ liệu
        }

        for (int colIndex = 0; colIndex < row.getPhysicalNumberOfCells(); colIndex++) {
            Cell cell = row.getCell(colIndex);
            if (cell != null && !cell.toString().trim().isEmpty()) {
                return true; // Dòng có dữ liệu
            }
        }
        return false; // Dòng không có dữ liệu
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
