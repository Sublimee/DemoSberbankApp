//package ru.sberbank.demo.app.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.collections4.CollectionUtils;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import ru.sberbank.demo.app.exception.AccountNotFoundException;
//import ru.sberbank.demo.app.exception.ClientNotFoundException;
//import ru.sberbank.demo.app.model.Account;
//import ru.sberbank.demo.app.model.Client;
//import ru.sberbank.demo.app.service.account.AccountService;
//
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(AccountsController.class)
//public class AccountControllerTest {
//
//    @MockBean
//    AccountService accountService;
//    @Autowired
//    private MockMvc mockMvc;
//
//    private static <T> T fromJSON(final TypeReference<T> type, final MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
//        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), type);
//    }
//
//    @Test
//    public void getClientAccountsOkTest() throws Exception {
//        UUID clientId = UUID.randomUUID();
//        Client client = new Client();
//        client.setId(clientId);
//
//        Account firstAccount = new Account(UUID.randomUUID(), client, 50L);
//        Account secondAccount = new Account(UUID.randomUUID(), client, 50L);
//
//        List<Account> expectedAccountList = new ArrayList<>();
//        expectedAccountList.add(firstAccount);
//        expectedAccountList.add(secondAccount);
//
//        when(accountService.getClientAccounts(anyLong())).thenReturn(expectedAccountList);
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/accounts/clients/{id}", 1L))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        List<Account> actualAccountList = fromJSON(new TypeReference<>() {
//        }, mvcResult);
//        assertTrue(CollectionUtils.isEqualCollection(expectedAccountList, actualAccountList));
//    }
//
//    @Test
//    public void getClientAccountsNotFoundTest() throws Exception {
//        when(accountService.getClientAccounts(anyLong())).thenThrow(ClientNotFoundException.class);
//        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/clients/{id}", 1L))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void getAccountByIdNotFoundTest() throws Exception {
//        when(accountService.findOne(anyLong())).thenThrow(AccountNotFoundException.class);
//        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/{id}", -1L))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void getAccountByIdOkTest() throws Exception {
//        Long clientId =1L;
//        Client client = new Client();
//        client.setId(UUID.randomUUID());
//
//        Account expectedAccount = new Account(UUID.randomUUID(), client, 50L);
//
//        when(accountService.findOne(anyLong())).thenReturn(expectedAccount);
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/accounts/{id}", 1L))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        Account actualAccount = fromJSON(new TypeReference<Account>() {
//        }, mvcResult);
//        assertEquals(expectedAccount, actualAccount);
//    }
//
//}
