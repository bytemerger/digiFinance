package com.bank.demo.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TransactionRequest {
    @NotNull
    @NotBlank(message = "account Number is required")
    protected String accountNumber;

    @NotNull
    @DecimalMin(value = "1.00", inclusive = true, message = "Deposit needs to be higher than 1.00")
    @DecimalMax(value = "1000000.00", inclusive = true, message = "Deposit needs to be less than 1000000.00")
    protected Double amount;
}
