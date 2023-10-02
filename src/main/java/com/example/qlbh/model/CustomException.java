package com.example.qlbh.model;


import java.sql.SQLException;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

}
