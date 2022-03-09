package com.mn.matdi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = {"com.mn.matdi.mapper"})
@SpringBootApplication
public class MatDiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatDiApiApplication.class, args);
    }

}
