package com.bank.demo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionList {
    private List<Transaction> list;

    public TransactionList(List<Transaction> list) {
        this.list = list;
    }
    public void addToList(Transaction transaction) {
        this.list.add(transaction);
    }

    public TransactionList(){
        super();
    }
}
