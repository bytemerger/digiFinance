package com.bank.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WithdrawRequest extends TransactionRequest{
    @NotNull
    @NotBlank(message = "Account Password is required")
    private String accountPassword;
}
