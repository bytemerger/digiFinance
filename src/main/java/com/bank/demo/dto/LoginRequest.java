package com.bank.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotBlank
    @NotBlank(message = "account Number is required")
    private String accountNumber;

    @NotNull
    @NotBlank(message = "account password is required")
    private String accountPassword;
}
