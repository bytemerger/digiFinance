package com.bank.demo.service;

import com.bank.demo.data.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={StoreService.class})
class StoreServiceTest {

    @Autowired
    private StoreService storeService;

    @Test
    void itShouldReadDataFromFile() throws IOException {
        File file = new File("db.json");
        if(file.exists()){
            Boolean clear = file.delete();
        }
        assertEquals("", storeService.readData());
        assertTrue(file.exists());
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("test");
        fileWriter.close();
        assertEquals("test", storeService.readData());
    }

    @Test
    void itWritesDataToDB() throws IOException {
        File file = new File("db.json");
        if(file.exists()){
            Boolean clear = file.delete();
        }
        Map<String, Map<String, Object>> testmap = new HashMap<>(){{
            put("test",new HashMap<>(){{
                put("testChild", new Account());
            }});
        }};
        assertTrue(storeService.writeData(testmap));
    }
}