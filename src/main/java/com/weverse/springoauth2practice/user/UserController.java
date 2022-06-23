package com.weverse.springoauth2practice.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UserController {

    @GetMapping
    String users() {
        return "";
    }
}
