package com.bank.demo.service;

import com.bank.demo.data.Account;
import com.bank.demo.dto.CreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private StoreService storeService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountService accountService;

    @Test
    void getCleanAccount() {

    }

    @Test
    void getAccountWithNoFileDb() throws IOException {
        given(storeService.readData()).willReturn("");
        ResponseStatusException res = assertThrows(ResponseStatusException.class,()->{ accountService.getAccount("1234567890") ;});
        assertEquals(res.getStatus().value(), 404);
    }
    @Test
    void itTestAccountDoesNotExist() throws IOException {
        given(storeService.readData()).willReturn("t");
        given(storeService.convertToMap("t")).willReturn(new HashMap<>(){{
            put("accounts",new HashMap<>(){{
                put("123456798", new Account("new user","123456798",400.00,"test"));
            }});
        }});
        ResponseStatusException res = assertThrows(ResponseStatusException.class,()->{ accountService.getAccount("1234567890") ;});
        assertEquals(res.getStatus().value(), 404);
    }

    @Test
    void getAccount() throws IOException {
        given(storeService.readData()).willReturn("t");
        given(storeService.convertToMap("t")).willReturn(new HashMap<>(){{
            put("accounts",new HashMap<>(){{
                put("123456798", new Account("new user","123456798",400.00,"test"));
            }});
        }});
        assertEquals(accountService.getAccount("123456798").getClass(), Account.class);
    }

    @Test
    void itTestCreateAccountNoDB() throws IOException {
        CreateRequest request = new CreateRequest();
        request.setAccountName("test");
        request.setAccountPassword("test");
        request.setInitialDeposit(400.00);
        given(storeService.readData()).willReturn("");
        assertEquals(accountService.createAccount(request).length(),10);
    }

    @Test
    void itTestIfUsernameExistCreateAccount() throws IOException {
        CreateRequest request = new CreateRequest();
        request.setAccountName("test");
        request.setAccountPassword("test");
        request.setInitialDeposit(400.00);
        given(storeService.readData()).willReturn("string");
        given(storeService.convertToMap("string")).willReturn(new HashMap<>(){{
            put("accounts",new HashMap<>(){{
                put("123456798", new Account("test","123456798",400.00,"test"));
            }});
        }});
        ResponseStatusException res = assertThrows(ResponseStatusException.class, ()->{accountService.createAccount(request);});
        assertEquals(res.getStatus().value(),409);
    }
    @Test
    void itTestCreateAccount() throws IOException {
        CreateRequest request = new CreateRequest();
        request.setAccountName("test");
        request.setAccountPassword("test");
        request.setInitialDeposit(400.00);
        given(storeService.readData()).willReturn("string");
        given(storeService.convertToMap("string")).willReturn(new HashMap<>(){{
            put("accounts",new HashMap<>(){{
                put("1234567980", new Account("new user","1234567980",400.00,"test"));
            }});
        }});
        assertEquals(accountService.createAccount(request).length(),10);
    }
    @Test
    void generateRandomNumbers() {
        String value = accountService.generateRandomNumbers(10);
        assertEquals(value.length(), 10);
    }

    @Test
    void loginAccount() {
    }
}