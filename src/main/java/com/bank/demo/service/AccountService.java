package com.bank.demo.service;

import com.bank.demo.data.Account;
import com.bank.demo.dto.CreateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AccountService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createAccount(CreateRequest request){
        request.setAccountPassword(passwordEncoder.encode(request.getAccountPassword()));
        String accountNo = generateRandomNumbers(10);
        Account account = new Account(request.getAccountName(), accountNo, request.getInitialDeposit(), request.getAccountPassword());
        try{
            String data = this.readData();
            if(data.isEmpty()){
                Map<String, Map<String, Object>> db = new HashMap<>();
                db.put("accounts", new HashMap<>(){{
                    put(account.getAccountNumber(), account);
                }});
                this.writeData(db);
                return true;
            }
            Map<String, Map<String, Object>> map = this.convertToMap(data);
            map.get("accounts").put(account.getAccountNumber(), account);
            this.writeData(map);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String generateRandomNumbers(int max){
        StringBuilder string = new StringBuilder(max);
        for(int i = 0; i<max; i++){
            Random randNum = new Random();
            int x = randNum.nextInt(9);
            string.append(x);
        }
        return string.toString();
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
