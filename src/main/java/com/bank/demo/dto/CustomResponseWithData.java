package com.bank.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponseWithData<T> {
    private int responseCode;
    private boolean success;
    private String message;
    private T data;

    public CustomResponseWithData(int responseCode, boolean success, String message, T data) {
        this.responseCode = responseCode;
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
