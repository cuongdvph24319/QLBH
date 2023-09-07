package com.example.qlbh.model;


import lombok.Getter;

import java.util.List;

@Getter
public class ImportResult {
    private final List<AccountRequest> successList;
    private final List<ImportError> errorList;

    public ImportResult(List<AccountRequest> successList, List<ImportError> errorList) {
        this.successList = successList;
        this.errorList = errorList;
    }

}
