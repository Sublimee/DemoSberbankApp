package ru.sberbank.demo.app.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.service.account.AccountService;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@SpringBootTest
@Tag("IntegrationTest")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"/import_clients_accounts.sql"})
class AccountServiceTest {

    @Autowired
    AccountService accountService;


    @Test
    void testGetAccountById() throws AccountNotFoundException {
        assertNotNull("Счет с указанным идентификатором не найден", accountService.getAccountById(1L));
    }

    @Test
    void testGetAccountByIncorrectId() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById(-1L));
    }

    @Test
    void testGetClientsAccounts() throws ClientNotFoundException, AccountNotFoundException {
        int size = accountService.getClientAccounts(1L).size();
        assertTrue("Количество клиентов в БД отличается от ожидаемого: " + size, size == 3);
    }

    @Test
    void testGetClientsAccountsByIncorrectId() {
        Assertions.assertThrows(ClientNotFoundException.class, () -> accountService.getClientAccounts(-1L));
    }

}
