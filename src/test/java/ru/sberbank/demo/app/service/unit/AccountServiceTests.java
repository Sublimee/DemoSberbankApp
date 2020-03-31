package ru.sberbank.demo.app.service.unit;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.model.Client;
import ru.sberbank.demo.app.repository.AccountRepository;
import ru.sberbank.demo.app.repository.ClientRepository;
import ru.sberbank.demo.app.service.account.AccountService;
import ru.sberbank.demo.app.service.account.AccountServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Tag("UnitTests")
@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {

    @Mock
    AccountRepository accountRepository;

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    AccountService accountService = new AccountServiceImpl();

    @Test
    public void getAccountByIdTest() throws Exception {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(new Account()));
        Account account = accountService.findOne(anyLong());
        assertEquals(new Account(), account);
    }

    @Test
    public void getAccountByIdExceptionTest() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.findOne(anyLong()));
    }

    @Test
    public void getClientAccountsTest() throws AccountNotFoundException, ClientNotFoundException {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(new Client()));
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account());
        when(accountRepository.getAccountsByClientId(anyLong())).thenReturn(Optional.of(accountList));
        List<Account> clientAccounts = accountService.getClientAccounts(anyLong());
        assertEquals(clientAccounts.size(), accountList.size());
    }

    @Test
    public void getClientAccountsExceptionTest() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> accountService.getClientAccounts(anyLong()));
    }

}
