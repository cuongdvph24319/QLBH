package com.example.qlbh.model;

import lombok.*;
import org.apache.poi.ss.usermodel.Cell;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImportError {
    private String rowNumber;

    private String columnName;

    private String cell;

    private String value;

    private String error;

}
