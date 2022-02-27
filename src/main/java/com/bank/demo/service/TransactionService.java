package com.bank.demo.service;

import com.bank.demo.data.Account;
import com.bank.demo.data.Transaction;
import com.bank.demo.data.TransactionList;
import com.bank.demo.dto.DepositRequest;
import com.bank.demo.dto.TransactionRequest;
import com.bank.demo.dto.WithdrawRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TransactionService {
    @Autowired
    private StoreService storeService;

    public enum TransactionType {
        DEPOSIT,
        WITHDRAW;
    }

    public boolean depositCash(DepositRequest depositRequest) throws IOException {
        return transactCash(depositRequest,TransactionType.DEPOSIT);
    }
    public boolean withdrawCash(WithdrawRequest withdrawRequest) throws IOException {
        return transactCash(withdrawRequest,TransactionType.WITHDRAW);
    }
    private <T extends TransactionRequest> boolean transactCash(T depositRequest, TransactionType transactionType) throws IOException {
        String db = storeService.readData();
        Map<String, Map<String, Object>> map = storeService.convertToMap(db);
        Map<String, Object> accounts = map.get("accounts");
        if(accounts.containsKey(depositRequest.getAccountNumber())){
            ObjectMapper mapper = new ObjectMapper();
            Account acc = mapper.convertValue(accounts.get(depositRequest.getAccountNumber()), Account.class);
            double newAmount;
            if(transactionType == TransactionType.WITHDRAW){
                if((acc.getBalance() - depositRequest.getAmount()) < 500){
                    throw new ResponseStatusException(BAD_REQUEST, "Account Balance cannot be less than 500");
                }
                newAmount = acc.getBalance() - depositRequest.getAmount();
            }else{
                newAmount = acc.getBalance() + depositRequest.getAmount();
            }
            acc.setBalance(newAmount);
            accounts.replace(depositRequest.getAccountNumber(), acc);
            String transactType = transactionType == TransactionType.WITHDRAW ? "withdrawal" : "deposit";
            Transaction transaction = new Transaction(LocalDateTime.now().toString(),transactType,"",depositRequest.getAmount(),newAmount);
            if(map.containsKey("transactions")){
                Map<String, Object> transactions = map.get("transactions");
                if(transactions.containsKey(depositRequest.getAccountNumber())){
                    TransactionList list = mapper.convertValue(transactions.get(depositRequest.getAccountNumber()), TransactionList.class);
                    list.addToList(transaction);
                    map.get("transactions").put(depositRequest.getAccountNumber(),list);
                    storeService.writeData(map);
                    return true;
                }
                List<Transaction> transactionList = new ArrayList<>();
                transactionList.add(transaction);
                map.get("transactions").put(depositRequest.getAccountNumber(), new TransactionList(transactionList));
                storeService.writeData(map);
                return true;
            }
            List<Transaction> transactionList = new ArrayList<>();
            transactionList.add(transaction);
            map.put("transactions",new HashMap<>(){{
                put(depositRequest.getAccountNumber(), new TransactionList(transactionList));
            }});
            storeService.writeData(map);
            return true;
        }
        throw new ResponseStatusException(NOT_FOUND, "Account not found");
    }
}
