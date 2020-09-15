package com.springboot.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloTest {

    @GetMapping("/")
    public String hello(){
        return "hello, spring security";
    }
}
