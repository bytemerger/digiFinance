package com.bank.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @GetMapping("/account_info/{account_number}")
    public String getAccountNumber(@PathVariable String account_number){
        return "hello world";
    }
    @GetMapping("/account_statement/{account_number}")
    public String getAccountStatement(@PathVariable String account_number){
        return "hello world";
    }
    @PostMapping("/create_account")
    public String create(){
        return "hello world";
    }
    @PostMapping("/login")
    public String login(){
        return "hello world";
    }
}
