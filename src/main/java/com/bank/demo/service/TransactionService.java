package com.bank.demo.service;

import com.bank.demo.data.Account;
import com.bank.demo.data.Transaction;
import com.bank.demo.data.TransactionList;
import com.bank.demo.dto.DepositRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TransactionService {
    public boolean depositCash(DepositRequest depositRequest) throws IOException {
        String db = this.readData();
        Map<String, Map<String, Object>> map = this.convertToMap(db);
        Map<String, Object> accounts = map.get("accounts");
        if(accounts.containsKey(depositRequest.getAccountNumber())){
            ObjectMapper mapper = new ObjectMapper();
            Account acc = mapper.convertValue(accounts.get(depositRequest.getAccountNumber()), Account.class);
            double transaction = acc.getBalance() + depositRequest.getAmount();
            acc.setBalance(transaction);
            accounts.replace(depositRequest.getAccountNumber(), acc);
            Transaction transaction1 = new Transaction(LocalDateTime.now().toString(),"Deposit","",depositRequest.getAmount(),transaction);
            if(map.containsKey("transactions")){
                Map<String, Object> transactions = map.get("transactions");
                //ObjectMapper mapper = new ObjectMapper();
                if(transactions.containsKey(depositRequest.getAccountNumber())){
                    TransactionList list = mapper.convertValue(transactions.get(depositRequest.getAccountNumber()), TransactionList.class);
                    list.addToList(transaction1);
                    map.get("transactions").put(depositRequest.getAccountNumber(),list);
                    this.writeData(map);
                    return true;
                }
                List<Transaction> transactionLis = new ArrayList<>();
                transactionLis.add(transaction1);
                map.get("transactions").put(depositRequest.getAccountNumber(), new TransactionList(transactionLis));
                this.writeData(map);
                return true;
            }
            /*map.put("transactions",new HashMap<>(){{
                put(depositRequest.getAccountNumber(), transaction1);
            }});*/
            List<Transaction> transactionList = new ArrayList<>();
            transactionList.add(transaction1);
            map.put("transactions",new HashMap<>(){{
                put(depositRequest.getAccountNumber(), new TransactionList(transactionList));
            }});
            this.writeData(map);
            return true;
        }
        throw new ResponseStatusException(NOT_FOUND, "Account not found");
    }
    private String readData() throws IOException {
        File file = new File("db.json");
        if(file.exists()){
            return new String(Files.readAllBytes(file.toPath()));
        }
        file.createNewFile();
        return "";
    }
    private Map<String, Map<String, Object>> convertToMap(String data ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String, Map<String, Object>>> typeRef
                = new TypeReference<Map<String, Map<String, Object>>>() {};
        return mapper.readValue(data, typeRef);
    }
    private void writeData(Map<String, Map<String, Object>> db) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(db);
        File file = new File("db.json");
        FileWriter fileWriter = new FileWriter(String.valueOf(file.toPath()));
        fileWriter.write(data);
        fileWriter.close();
    }
}
