package com.bank.demo.controllers;

import com.bank.demo.data.Account;
import com.bank.demo.data.Transaction;
import com.bank.demo.dto.CreateRequest;
import com.bank.demo.dto.CustomResponse;
import com.bank.demo.dto.CustomResponseWithData;
import com.bank.demo.dto.LoginRequest;
import com.bank.demo.service.AccountService;
import com.bank.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/account_info/{account_number}")
    public ResponseEntity<?> getAccountNumber(@PathVariable String account_number){
        try {
            Map<String, String> account = accountService.getCleanAccount(account_number);
            return new ResponseEntity<>(new CustomResponseWithData<>(200, true, "successful", account), HttpStatus.valueOf(200));
        }catch (IOException ex){
            return new ResponseEntity<>(new CustomResponse(500, false, "An error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/account_statement/{account_number}")
    public ResponseEntity<List<Transaction>> getAccountStatement(@PathVariable String account_number) throws IOException {
        return new ResponseEntity<>(transactionService.getAccountStatement(account_number), HttpStatus.OK);
    }
    @PostMapping("/create_account")
    public ResponseEntity<CustomResponse> create(@Valid @RequestBody CreateRequest request){
        try{
            boolean result = accountService.createAccount(request);
            if(result){
                return new ResponseEntity<>(new CustomResponse(200,true,"successfully created"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new CustomResponse(500,false,"An error Occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(new CustomResponse(e.getStatus().value(),false,e.getMessage()), e.getStatus());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) throws IOException {
        try {
            String result = accountService.loginAccount(request);
            return new ResponseEntity<>(new HashMap<>(){{
                put("success",true);
                put("token",result);
            }},HttpStatus.OK);
        } catch (ResponseStatusException ex){
            return new ResponseEntity<>(new CustomResponse(ex.getStatus().value(), false, ex.getMessage()), ex.getStatus());
        }
    }
}
