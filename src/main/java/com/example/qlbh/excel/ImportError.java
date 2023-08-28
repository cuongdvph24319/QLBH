package com.example.qlbh.excel;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImportError {
    private String rowNumber;

    private String value;

    private String error;
}
