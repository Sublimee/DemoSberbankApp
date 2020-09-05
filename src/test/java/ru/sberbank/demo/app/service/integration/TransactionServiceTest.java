//package ru.sberbank.demo.app.service.integration;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import ru.sberbank.demo.app.exception.AccountNotFoundException;
//import ru.sberbank.demo.app.exception.ResourceNotFoundException;
//import ru.sberbank.demo.app.exception.transaction.DepositTransactionException;
//import ru.sberbank.demo.app.exception.transaction.TransferTransactionException;
//import ru.sberbank.demo.app.exception.transaction.WithdrawTransactionException;
//import ru.sberbank.demo.app.service.account.AccountService;
//import ru.sberbank.demo.app.service.transaction.TransactionService;
//
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
//import static org.springframework.test.util.AssertionErrors.assertTrue;
//
//
//@SpringBootTest
//@Tag("IntegrationTests")
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"/import_clients_accounts.sql"})
//class TransactionServiceTest {
//
//    private final Long ACCOUNT_AMOUNT = 2000L;
//    private final Long OTHER_ACCOUNT_AMOUNT = 5000L;
//    private final Long ZERO_AMOUNT = 0L;
//    private final Long NEGATIVE_AMOUNT = -50L;
//    private final Long POSITIVE_AMOUNT = 50L;
//    private final Long INCORRECT_POSITIVE_AMOUNT = Long.MAX_VALUE;
//    private final Long ACCOUNT_ID = 1L;
//    private final Long PAYEE_ACCOUNT_ID = 3L;
//    private final Long INCORRECT_ACCOUNT_ID = -1L;
//    private final Long INCORRECT_PAYEE_ACCOUNT_ID = -3L;
//
//    @Autowired
//    private TransactionService transactionsService;
//    @Autowired
//    private AccountService accountService;
//
//    @Test
//    void depositTest() throws AccountNotFoundException, DepositTransactionException, ResourceNotFoundException {
//        transactionsService.deposit(ACCOUNT_ID, POSITIVE_AMOUNT);
//        Long balance = accountService.findOne(ACCOUNT_ID).getBalance();
//        assertTrue("Сумма на счете не совпадает с ожидаемой: " + balance, balance == ACCOUNT_AMOUNT + POSITIVE_AMOUNT);
//    }
//
//    @Test
//    void depositWithZeroAmountExceptionTest() {
//        Assertions.assertThrows(DepositTransactionException.class, () -> transactionsService.deposit(ACCOUNT_ID, ZERO_AMOUNT));
//    }
//
//    @Test
//    void depositWithNegativeAmountTest() {
//        Assertions.assertThrows(DepositTransactionException.class, () -> transactionsService.deposit(ACCOUNT_ID, NEGATIVE_AMOUNT));
//    }
//
//    @Test
//    void depositWithIncorrectAccountIdTest() {
//        Assertions.assertThrows(AccountNotFoundException.class, () -> transactionsService.deposit(INCORRECT_ACCOUNT_ID, POSITIVE_AMOUNT));
//    }
//
//    @Test
//    void withdrawTest() throws AccountNotFoundException, WithdrawTransactionException, ResourceNotFoundException {
//        transactionsService.withdraw(ACCOUNT_ID, POSITIVE_AMOUNT);
//        Long balance = accountService.findOne(ACCOUNT_ID).getBalance();
//        assertTrue("Сумма на счете не совпадает с ожидаемой: " + balance, balance == ACCOUNT_AMOUNT - POSITIVE_AMOUNT);
//    }
//
//    @Test
//    void withdrawWithZeroAmountExceptionTest() {
//        Assertions.assertThrows(WithdrawTransactionException.class, () -> transactionsService.withdraw(ACCOUNT_ID, ZERO_AMOUNT));
//    }
//
//    @Test
//    void withdrawWithNegativeAmountExceptionTest() {
//        Assertions.assertThrows(WithdrawTransactionException.class, () -> transactionsService.withdraw(ACCOUNT_ID, NEGATIVE_AMOUNT));
//    }
//
//    @Test
//    void withdrawWithIncorrectClientIdExceptionTest() {
//        Assertions.assertThrows(AccountNotFoundException.class, () -> transactionsService.withdraw(INCORRECT_ACCOUNT_ID, POSITIVE_AMOUNT));
//    }
//
//    @Test
//    void withdrawWithIncorrectBigAmountExceptionTest() {
//        Assertions.assertThrows(WithdrawTransactionException.class, () -> transactionsService.withdraw(ACCOUNT_ID, INCORRECT_POSITIVE_AMOUNT));
//    }
//
//    @Test
//    void transferTest() throws AccountNotFoundException, TransferTransactionException, ResourceNotFoundException {
//        transactionsService.transfer(ACCOUNT_ID, PAYEE_ACCOUNT_ID, POSITIVE_AMOUNT);
//        Long accountBalance = accountService.findOne(ACCOUNT_ID).getBalance();
//        Long otherAccountBalance = accountService.findOne(PAYEE_ACCOUNT_ID).getBalance();
//        assertTrue("Сумма на счете отправителя не совпадает с ожидаемой: " + accountBalance, accountBalance.equals(ACCOUNT_AMOUNT - POSITIVE_AMOUNT));
//        assertTrue("Сумма на счете получателя не совпадает с ожидаемой: " + otherAccountBalance, otherAccountBalance.equals(OTHER_ACCOUNT_AMOUNT + POSITIVE_AMOUNT));
//    }
//
//    @Test
//    void transferWithZeroAmountExceptionTest() {
//        Assertions.assertThrows(TransferTransactionException.class, () -> transactionsService.transfer(ACCOUNT_ID, PAYEE_ACCOUNT_ID, ZERO_AMOUNT));
//    }
//
//    @Test
//    void transferWithNegativeAmountExceptionTest() {
//        Assertions.assertThrows(TransferTransactionException.class, () -> transactionsService.transfer(ACCOUNT_ID, PAYEE_ACCOUNT_ID, NEGATIVE_AMOUNT));
//    }
//
//    @Test
//    void transferWithIncorrectPayerAccountAndCorrectAmountExceptionTest() {
//        Assertions.assertThrows(AccountNotFoundException.class, () -> transactionsService.transfer(INCORRECT_ACCOUNT_ID, PAYEE_ACCOUNT_ID, POSITIVE_AMOUNT));
//    }
//
//    @Test
//    void transferWithIncorrectPayeeAccountAndCorrectAmountExceptionTest() {
//        Assertions.assertThrows(AccountNotFoundException.class, () -> transactionsService.transfer(ACCOUNT_ID, INCORRECT_PAYEE_ACCOUNT_ID, POSITIVE_AMOUNT));
//    }
//
//    @Test
//    void transferWithIncorrectAccountsAndCorrectAmountExceptionTest() {
//        Assertions.assertThrows(AccountNotFoundException.class, () -> transactionsService.transfer(INCORRECT_ACCOUNT_ID, INCORRECT_PAYEE_ACCOUNT_ID, POSITIVE_AMOUNT));
//    }
//
//    @Test
//    void transferWithIncorrectAccountsAndBigAmountExceptionTest() {
//        Assertions.assertThrows(AccountNotFoundException.class, () -> transactionsService.transfer(INCORRECT_ACCOUNT_ID, INCORRECT_PAYEE_ACCOUNT_ID, INCORRECT_POSITIVE_AMOUNT));
//    }
//
//}
