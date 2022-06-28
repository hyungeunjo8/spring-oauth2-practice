package com.weverse.springoauth2practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(securedEnabled = true)
@SpringBootApplication
public class SpringOauth2PracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringOauth2PracticeApplication.class, args);
    }

}
