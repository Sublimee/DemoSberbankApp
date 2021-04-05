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
import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.service.account.AccountService;

import java.util.UUID;

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


//    @Test
//    void testGetAccountById() throws AccountNotFoundException, ResourceNotFoundException {
//        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
//        assertNotNull("Счет с указанным идентификатором не найден", accountService.findOne(uuid));
//    }

    @Test
    void testGetAccountByIncorrectId() {
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-999999999999");
        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.findOne(uuid));
    }

    @Test
    void testGetClientsAccounts() throws ResourceNotFoundException {
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
        int size = accountService.getClientAccounts(uuid).size();
        assertTrue("Количество клиентов в БД отличается от ожидаемого: " + size, size == 3);
    }

    @Test
    void testGetClientsAccountsByIncorrectId() {
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-999999999999");
        Assertions.assertThrows(ClientNotFoundException.class, () -> accountService.getClientAccounts(uuid));
    }

}
