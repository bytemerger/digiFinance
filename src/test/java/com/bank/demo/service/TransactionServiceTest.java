package com.bank.demo.service;

import com.bank.demo.data.Account;
import com.bank.demo.dto.DepositRequest;
import com.bank.demo.dto.WithdrawRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.lang.reflect.WildcardType;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private StoreService storeService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void depositCashAccountDoesNotExist() throws IOException {
        DepositRequest request = new DepositRequest();
        request.setAccountNumber("0000000001");
        request.setAmount(100.00);
        given(storeService.readData()).willReturn("string");
        given(storeService.convertToMap("string")).willReturn(new HashMap<>(){{
            put("accounts",new HashMap<>(){{
                put("123456798", new Account("test","123456798",400.00,"test"));
            }});
        }});
        ResponseStatusException res = assertThrows(ResponseStatusException.class, ()->{transactionService.depositCash(request);});
        assertEquals(res.getStatus().value(),404);
    }

    @Test
    void depositCashAccountAccountDbEmpty() throws IOException {
        DepositRequest request = new DepositRequest();
        request.setAccountNumber("0000000001");
        request.setAmount(100.00);
        given(storeService.readData()).willReturn("string");
        given(storeService.convertToMap("string")).willReturn(new HashMap<>(){{
            put("accounts",new HashMap<>(){{
                put("0000000001", new Account("test","0000000001",400.00,"test"));
            }});
        }});
        assertTrue(transactionService.depositCash(request));
    }
    @Test
    void withdrawCash() {
    }

    @Test
    void withdrawCashAccountBalance() throws IOException {
        WithdrawRequest request = new WithdrawRequest();
        request.setAccountNumber("0000000001");
        request.setAmount(800.00);
        request.setAccountPassword("test");
        given(storeService.readData()).willReturn("string");
        given(storeService.convertToMap("string")).willReturn(new HashMap<>(){{
            put("accounts",new HashMap<>(){{
                put("0000000001", new Account("test","0000000001",400.00,"test"));
            }});
        }});
        ResponseStatusException res = assertThrows(ResponseStatusException.class, ()->{ transactionService.withdrawCash(request);});
        assertEquals(res.getReason(),"Account Balance cannot be less than 500");
    }

    @Test
    void withdrawWithAccountDB() throws IOException {
        WithdrawRequest request = new WithdrawRequest();
        request.setAccountNumber("0000000001");
        request.setAmount(100.00);
        request.setAccountPassword("test");
        given(storeService.readData()).willReturn("string");
        given(storeService.convertToMap("string")).willReturn(new HashMap<>(){{
            put("accounts",new HashMap<>(){{
                put("0000000001", new Account("test","0000000001",1000.00,"test"));
            }});
            put("transactions", new HashMap<>());
        }});
        assertTrue(transactionService.withdrawCash(request));
    }
    @Test
    void getAccountStatement() {
    }
}