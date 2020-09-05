//package ru.sberbank.demo.app.repository;
//
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import ru.sberbank.demo.app.model.Account;
//import ru.sberbank.demo.app.model.transaction.DepositTransaction;
//
//import java.util.UUID;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
//
//@SpringBootTest
//@Tag("IntegrationTests")
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"/import_clients_accounts.sql"})
//class DepositTransactionRepositoryTest {
//
//    @Autowired
//    private DepositTransactionRepository depositTransactionRepository;
//
//    @Test
//    void depositFindAllTest() {
//        Account account = new Account();
//        account.setId(UUID.randomUUID());
//        DepositTransaction depositTransaction = new DepositTransaction();
//        depositTransaction.setAccount(account);
//        depositTransaction.setTransferAmount(50L);
//        depositTransaction.setId(UUID.randomUUID());
//        DepositTransaction savedTransaction = depositTransactionRepository.save(depositTransaction);
//        assertThat(savedTransaction.getId(), equalTo(1L));
//        assertThat(savedTransaction.getTransferAmount(), equalTo(50L));
//    }
//
//    @Test
//    void depositPaginationTest() {
//        for (int i = 0; i < 5; i++) {
//            Account account = new Account();
//            account.setId(UUID.randomUUID());
//            DepositTransaction depositTransaction = new DepositTransaction();
//            depositTransaction.setAccount(account);
//            depositTransaction.setTransferAmount(50L);
//            depositTransactionRepository.save(depositTransaction);
//        }
//        Page<DepositTransaction> depositTransactions = depositTransactionRepository.findAll(PageRequest.of(1, 3));
//        assertThat(depositTransactions.getTotalElements(), equalTo(5L));
//        assertThat(depositTransactions.getContent(), hasSize(2));
//    }
//
//}