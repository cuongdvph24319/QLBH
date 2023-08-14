package com.example.qlbh.config;

import com.example.qlbh.utils.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean("utils")
    public Utils getappendLike(){
        return new Utils();
    }
}
