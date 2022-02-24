package com.bank.demo.controllers;

import org.springframework.web.bind.annotation.PostMapping;

public class TransactionController {
    @PostMapping("/deposit")
    public String deposit(){
        return "hello world";
    }
    @PostMapping("/withdrawal")
    public String withdraw(){
        return "hello world";
    }
}
