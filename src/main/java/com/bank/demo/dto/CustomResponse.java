package com.bank.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponse{
    private int responseCode;
    private boolean success;
    private String message;

    public CustomResponse(int responseCode, boolean success, String message) {
        this.responseCode = responseCode;
        this.success = success;
        this.message = message;
    }
}
