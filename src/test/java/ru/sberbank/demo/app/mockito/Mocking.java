package ru.sberbank.demo.app.mockito;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.repository.AccountsRepository;
import ru.sberbank.demo.app.repository.ClientsRepository;
import ru.sberbank.demo.app.service.account.AccountsService;
import ru.sberbank.demo.app.service.account.AccountsServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class Mocking {

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

}
