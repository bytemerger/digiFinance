package com.bank.demo.controllers;

import com.bank.demo.dto.CreateRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String create(@Valid @RequestBody CreateRequest request){
        return request.getAccountName();
    }
    @PostMapping("/login")
    public String login(){
        return "hello world";
    }
}
