package com.bank.demo.dto;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateRequest {
    @NotBlank(message = "account name is required")
    private String accountName;

    @NotBlank(message = "account password is required")
    private String accountPassword;

    @NotNull(message = "Initial Deposit is required")
    @DecimalMin(value = "500.00", inclusive = true, message = "Deposit needs to be higher than 500.00")
    private Double initialDeposit;
}
