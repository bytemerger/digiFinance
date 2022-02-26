package com.bank.demo.data;

import lombok.Data;

@Data
public class Transaction {
    private String transactionDate;
    private String transactionType;
    private String narration;
    private Double amount;
    private Double balanceAfterTran;

    public Transaction(String transactionDate, String transactionType, String narration, Double amount, Double balanceAfterTran) {
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.narration = narration;
        this.amount = amount;
        this.balanceAfterTran = balanceAfterTran;
    }
    public Transaction(){
        super();
    }
}
