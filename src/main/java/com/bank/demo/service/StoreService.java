package com.bank.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@Service
public class StoreService {
    public String readData() throws IOException {
        File file = new File("db.json");
        if(file.exists()){
            return new String(Files.readAllBytes(file.toPath()));
        }
        file.createNewFile();
        return "";
    }
    public Map<String, Map<String, Object>> convertToMap(String data ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String, Map<String, Object>>> typeRef
                = new TypeReference<Map<String, Map<String, Object>>>() {};
        return mapper.readValue(data, typeRef);
    }
    public boolean writeData(Map<String, Map<String, Object>> db) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(db);
        File file = new File("db.json");
        FileWriter fileWriter = new FileWriter(String.valueOf(file.toPath()));
        fileWriter.write(data);
        fileWriter.close();
        return true;
    }
}
