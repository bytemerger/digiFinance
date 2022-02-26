package com.bank.demo.data;

import lombok.Data;

@Data
public class Account {
    private String accountName;
    private String accountNumber;
    private Double balance;
    private String password;

    public Account(String accountName, String accountNumber, Double balance, String password) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.password = password;
    }
    public Account(){
        super();
    }
}
