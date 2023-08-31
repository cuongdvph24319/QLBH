package com.example.qlbh.excel;

import lombok.*;
import org.apache.poi.ss.usermodel.Cell;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImportError {
    private String rowNumber;

    private String column;

    private String cell;

    private String value;

    private String error;

}
