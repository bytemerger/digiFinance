package com.bank.demo.data;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account {
    private String accountName;
    private String accountNumber;
    private Double balance;
    private String password;
}
