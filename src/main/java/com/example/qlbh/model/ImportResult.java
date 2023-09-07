package com.example.qlbh.model;


import lombok.Getter;

import java.util.List;

@Getter
public class ImportResult {
    private final String successList;
    private final List<ImportError> errorList;

    public ImportResult(String successList, List<ImportError> errorList) {
        this.successList = successList;
        this.errorList = errorList;
    }

}
