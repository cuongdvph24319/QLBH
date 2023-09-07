package com.example.qlbh.service.Impl;

import com.example.qlbh.entity.Account;
import com.example.qlbh.model.AccountDTO;
import com.example.qlbh.model.AccountRequest;
import com.example.qlbh.model.ImportError;
import com.example.qlbh.model.ImportResult;
import com.example.qlbh.repository.AccountRepository;
import com.example.qlbh.repository.RelationRepository;
import com.example.qlbh.service.AccountService;
import com.example.qlbh.utils.Utils;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Resource(name = "accountRepository")
    AccountRepository accountRepository;

    @Resource(name = "relationRepository")
    RelationRepository relationRepository;


    @Override
    public Page<Account> getAll(String ma, String ten, String hoten, Pageable pageable) {
        return accountRepository.getAll(ma, ten, hoten, pageable);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImportResult saveExcelData(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        CellReference cellRef;
        Sheet sheet = workbook.getSheet("Accounts");

        // định dạng giá trị trong Excel
        DataFormatter formatter = new DataFormatter();

        List<AccountRequest> requestList = new ArrayList<>();
        List<ImportError> errorList = new ArrayList<>();
        List<String> listMa = accountRepository.getAllCode();
        List<Integer> listRelation = relationRepository.getAllId();

        for (int rowIndex = 0; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            String messageError = "Không để trống ";
            String columnId = "relation_id";
            String columnCode = "ma";
            String columnName = "ten";
            String columnPass = "mat khau";
            String columnEmail = "email";
            if (rowIndex == 0 || !rowHasData(row)) {
                continue;
            }

            Cell relationIdCell = row.getCell(0);
            Cell maCell = row.getCell(1);
            Cell tenCell = row.getCell(2);
            Cell matKhauCell = row.getCell(3);
            Cell emailCell = row.getCell(4);

            Integer relationId = null;
            String ma = formatter.formatCellValue(maCell);
            String ten = formatter.formatCellValue(tenCell);
            String matKhau = formatter.formatCellValue(matKhauCell);
            String email = formatter.formatCellValue(emailCell);

            try {
                relationId = Integer.valueOf(formatter.formatCellValue(relationIdCell));
            } catch (NumberFormatException ex) {
                // check id rong
                if (relationIdCell != null && relationIdCell.getCellType() == CellType.STRING && !relationIdCell.getStringCellValue().trim().isEmpty()) {
                    cellRef = new CellReference(row.getRowNum(), relationIdCell.getColumnIndex());
                    addErrorIfNotEmpty(errorList, row, columnId, cellRef, relationIdCell, formatter, columnId + "không thể là chữ");
                } else if (relationIdCell != null && relationIdCell.getStringCellValue().trim().isEmpty()) {
                    cellRef = new CellReference(row.getRowNum(), relationIdCell.getColumnIndex());
                    addErrorIfNotEmpty(errorList, row, columnId, cellRef, relationIdCell, formatter, messageError + columnId);
                } else {
                    cellRef = new CellReference(row.getRowNum(), 0);
                    addErrorIfEmpty(errorList, row, columnId, formatter.formatCellValue(relationIdCell), relationIdCell, formatter, messageError + columnId, cellRef.formatAsString());
                }
            }

            // check ma
            if (maCell != null && maCell.getStringCellValue().trim().isEmpty()) {
                cellRef = new CellReference(row.getRowNum(), maCell.getColumnIndex());
                addErrorIfNotEmpty(errorList, row, columnCode, cellRef, maCell, formatter, messageError + columnCode);

            } else {
                cellRef = new CellReference(row.getRowNum(), 1);
                addErrorIfEmpty(errorList, row, columnCode, ma, maCell, formatter, messageError + columnCode, cellRef.formatAsString());
            }
            // check ten
            if (tenCell != null && tenCell.getStringCellValue().trim().isEmpty()) {
                cellRef = new CellReference(row.getRowNum(), tenCell.getColumnIndex());
                addErrorIfNotEmpty(errorList, row, columnName, cellRef, tenCell, formatter, messageError + columnName);

            } else {
                cellRef = new CellReference(row.getRowNum(), 2);
                addErrorIfEmpty(errorList, row, columnName, ten, tenCell, formatter, messageError + columnName, cellRef.formatAsString());
            }
            // check mat khau
            if (matKhauCell != null && matKhauCell.getStringCellValue().trim().isEmpty()) {
                cellRef = new CellReference(row.getRowNum(), matKhauCell.getColumnIndex());
                addErrorIfNotEmpty(errorList, row, columnPass, cellRef, matKhauCell, formatter, messageError + columnPass);
            } else {
                cellRef = new CellReference(row.getRowNum(), 3);
                addErrorIfEmpty(errorList, row, columnPass, matKhau, matKhauCell, formatter, messageError + columnPass, cellRef.formatAsString());
            }
            // check email
            if (emailCell != null && emailCell.getStringCellValue().trim().isEmpty()) {
                cellRef = new CellReference(row.getRowNum(), 4);
                addErrorIfEmpty(errorList, row, columnEmail, email, emailCell, formatter, messageError + columnEmail, cellRef.formatAsString());
            }
            // check ton tai relation_id
            if (!listRelation.contains(relationId)) {
                cellRef = new CellReference(row.getRowNum(), 0);
                addErrorIfNotEmpty(errorList, row, columnId, cellRef, relationIdCell, formatter, "Không tồn tại " + columnId);
            }
            // check trung ma
            if (listMa.contains(ma)) {
                cellRef = new CellReference(row.getRowNum(), 1);
                addErrorIfNotEmpty(errorList, row, columnCode, cellRef, maCell, formatter, columnCode + " đã tồn tại");
            }

            AccountRequest accountRequest = new AccountRequest(
                    relationId,
                    ma,
                    ten,
                    matKhau,
                    email
            );
            requestList.add(accountRequest);
        }

        if (!errorList.isEmpty()) {
            return new ImportResult(new ArrayList<>(), errorList);
        } else {
            List<Account> accounts = requestList.stream().map(Account::new).collect(Collectors.toList());
            accountRepository.saveAll(accounts);
            List<AccountRequest> accountRequestList = accounts.stream().map(AccountRequest::new).collect(Collectors.toList());
            return new ImportResult(accountRequestList, new ArrayList<>());
        }


    }

    private void addErrorIfNotEmpty(
            List<ImportError> errorList,
            Row row,
            String columnName,
            CellReference cellRef,
            Cell cell,
            DataFormatter formatter,
            String errorMessage
    ) {
        if (cell != null && !formatter.formatCellValue(cell).trim().isEmpty()) {
            ImportError importError = new ImportError(
                    String.valueOf(row.getRowNum() + 1),
                    columnName,
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
            String columnName,
            String value,
            Cell cell,
            DataFormatter formatter,
            String errorMessage,
            String cells
    ) {
        if (value.isEmpty()) {
            ImportError importError = new ImportError(
                    String.valueOf(row.getRowNum() + 1),
                    columnName,
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
//                        root.get("ten"),
//                        Utils.appendLike(tenAccount)
//                ));
//            }
//            if (tenNQH != null) {
//                predicates.add(criteriaBuilder.like(
//                        root.get("relation").get("hoTen"), Utils.appendLike(tenNQH)
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
