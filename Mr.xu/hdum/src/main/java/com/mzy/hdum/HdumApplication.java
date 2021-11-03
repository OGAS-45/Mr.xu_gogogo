package com.mzy.hdum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;


@SpringBootApplication
@MapperScan("com.mzy.hdum.mapper")
public class HdumApplication {

    public static void main(String[] args) {
        SpringApplication.run(HdumApplication.class, args);
    }

}
