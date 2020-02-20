package ru.sberbank.demo.app.unit;


import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sberbank.demo.app.category.UnitTests;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.model.Client;
import ru.sberbank.demo.app.repository.AccountsRepository;
import ru.sberbank.demo.app.repository.ClientsRepository;
import ru.sberbank.demo.app.service.account.AccountsService;
import ru.sberbank.demo.app.service.account.AccountsServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Category(UnitTests.class)
@ExtendWith(MockitoExtension.class)
public class AccountsServiceTests {

    @Mock
    AccountsRepository accountsRepository;

    @Mock
    ClientsRepository clientsRepository;

    @InjectMocks
    AccountsService accountsService = new AccountsServiceImpl();

    @Test
    public void getAccountByIdTest() throws Exception {
        when(accountsRepository.getAccountById(anyLong())).thenReturn(Optional.of(new Account()));
        Account account = accountsService.getAccountById(anyLong());
        assertEquals(new Account(), account);
    }

    @Test
    public void getAccountByIdExceptionTest() {
        when(accountsRepository.getAccountById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountsService.getAccountById(anyLong()));
    }

    @Test
    public void getClientAccountsTest() throws AccountNotFoundException, ClientNotFoundException {
        when(clientsRepository.findById(anyLong())).thenReturn(Optional.of(new Client()));
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account());
        when(accountsRepository.getAccountsByClientId(anyLong())).thenReturn(Optional.of(accountList));
        List<Account> clientAccounts = accountsService.getClientAccounts(anyLong());
        assertEquals(clientAccounts.size(), accountList.size());
    }

    @Test
    public void getClientAccountsExceptionTest() {
        when(clientsRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> accountsService.getClientAccounts(anyLong()));
    }

}
