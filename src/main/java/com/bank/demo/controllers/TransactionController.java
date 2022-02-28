package com.bank.demo.controllers;

import com.bank.demo.dto.CustomResponse;
import com.bank.demo.dto.DepositRequest;
import com.bank.demo.dto.WithdrawRequest;
import com.bank.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<CustomResponse> deposit(@Valid @RequestBody DepositRequest request){
        try {
            boolean result = transactionService.depositCash(request);
            return new ResponseEntity<>(new CustomResponse(200, true, "successful"), HttpStatus.valueOf(200));
        }catch (IOException ex){
            return new ResponseEntity<>(new CustomResponse(500, false, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/withdrawal")
    public ResponseEntity<CustomResponse> withdraw(@Valid @RequestBody WithdrawRequest request){
        try {
            boolean result = transactionService.withdrawCash(request);
            return new ResponseEntity<>(new CustomResponse(200, true, "successful"), HttpStatus.valueOf(200));
        }catch (IOException ex){
            return new ResponseEntity<>(new CustomResponse(500, false, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
