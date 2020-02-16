package ru.sberbank.demo.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.transaction.DepositTransactionException;
import ru.sberbank.demo.app.exception.transaction.TransferTransactionException;
import ru.sberbank.demo.app.exception.transaction.WithdrawTransactionException;
import ru.sberbank.demo.app.service.account.AccountsService;
import ru.sberbank.demo.app.service.transaction.TransactionsService;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"/import_clients_accounts.sql"})
class TransactionsServiceTests {

    @Autowired
    TransactionsService transactionsService;

    @Autowired
    AccountsService accountsService;

    private final Long ACCOUNT_AMOUNT = 2000L;
    private final Long OTHER_ACCOUNT_AMOUNT = 5000L;

    private final Long ZERO_AMOUNT = 0L;
    private final Long NEGATIVE_AMOUNT = -50L;
    private final Long CORRECT_AMOUNT = 50L;
    private final Long INCORRECT_BIG_AMOUNT = ACCOUNT_AMOUNT + CORRECT_AMOUNT;

    private final Long ACCOUNT_ID = 1L;
    private final Long PAYEE_ACCOUNT_ID = 3L;

    private final Long INCORRECT_ACCOUNT_ID = -1L;
    private final Long INCORRECT_PAYEE_ACCOUNT_ID = -3L;

    @Test
    void testDeposit() throws AccountNotFoundException, DepositTransactionException {
        transactionsService.deposit(ACCOUNT_ID, CORRECT_AMOUNT);
        Long balance = accountsService.getAccountById(ACCOUNT_ID).getBalance();
        assertTrue("Сумма на счете не совпадает с ожидаемой: " + balance, balance == ACCOUNT_AMOUNT + CORRECT_AMOUNT);
    }

    @Test
    void testDepositWithZeroAmount() {
        Assertions.assertThrows(DepositTransactionException.class, () -> transactionsService.deposit(ACCOUNT_ID, ZERO_AMOUNT));
    }

    @Test
    void testDepositWithNegativeAmount() {
        Assertions.assertThrows(DepositTransactionException.class, () -> transactionsService.deposit(ACCOUNT_ID, NEGATIVE_AMOUNT));
    }

    @Test
    void testDepositWithIncorrectAccountId() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> transactionsService.deposit(INCORRECT_ACCOUNT_ID, CORRECT_AMOUNT));
    }

    @Test
    void testWithdraw() throws AccountNotFoundException, WithdrawTransactionException {
        transactionsService.withdraw(ACCOUNT_ID, CORRECT_AMOUNT);
        Long balance = accountsService.getAccountById(ACCOUNT_ID).getBalance();
        assertTrue("Сумма на счете не совпадает с ожидаемой: " + balance, balance == ACCOUNT_AMOUNT - CORRECT_AMOUNT);
    }

    @Test
    void testWithdrawWithZeroAmount() {
        Assertions.assertThrows(WithdrawTransactionException.class, () -> transactionsService.withdraw(ACCOUNT_ID, ZERO_AMOUNT));
    }

    @Test
    void testWithdrawWithNegativeAmount() {
        Assertions.assertThrows(WithdrawTransactionException.class, () -> transactionsService.withdraw(ACCOUNT_ID, NEGATIVE_AMOUNT));
    }

    @Test
    void testWithdrawWithIncorrectClientId() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> transactionsService.withdraw(INCORRECT_ACCOUNT_ID, CORRECT_AMOUNT));
    }

    @Test
    void testWithdrawWithIncorrectBigAmount() {
        Assertions.assertThrows(WithdrawTransactionException.class, () -> transactionsService.withdraw(ACCOUNT_ID, INCORRECT_BIG_AMOUNT));
    }

    @Test
    void testTransfer() throws AccountNotFoundException, TransferTransactionException {
        transactionsService.transfer(ACCOUNT_ID, PAYEE_ACCOUNT_ID, CORRECT_AMOUNT);
        Long accountBalance = accountsService.getAccountById(ACCOUNT_ID).getBalance();
        Long otherAccountBalance = accountsService.getAccountById(PAYEE_ACCOUNT_ID).getBalance();
        assertTrue("Сумма на счете отправителя не совпадает с ожидаемой: " + accountBalance, accountBalance == ACCOUNT_AMOUNT - CORRECT_AMOUNT);
        assertTrue("Сумма на счете получателя не совпадает с ожидаемой: " + otherAccountBalance, otherAccountBalance == OTHER_ACCOUNT_AMOUNT + CORRECT_AMOUNT);
    }

    @Test
    void testTransferWithZeroAmount() {
        Assertions.assertThrows(TransferTransactionException.class, () -> transactionsService.transfer(ACCOUNT_ID, PAYEE_ACCOUNT_ID, ZERO_AMOUNT));
    }

    @Test
    void testTransferWithNegativeAmount() {
        Assertions.assertThrows(TransferTransactionException.class, () -> transactionsService.transfer(ACCOUNT_ID, PAYEE_ACCOUNT_ID, NEGATIVE_AMOUNT));
    }

    @Test
    void testTransferWithIncorrectPayerAccountAndCorrectAmount() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> transactionsService.transfer(INCORRECT_ACCOUNT_ID, PAYEE_ACCOUNT_ID, CORRECT_AMOUNT));
    }

    @Test
    void testTransferWithIncorrectPayeeAccountAndCorrectAmount() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> transactionsService.transfer(ACCOUNT_ID, INCORRECT_PAYEE_ACCOUNT_ID, CORRECT_AMOUNT));
    }

    @Test
    void testTransferWithIncorrectAccountsAndCorrectAmount() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> transactionsService.transfer(INCORRECT_ACCOUNT_ID, INCORRECT_PAYEE_ACCOUNT_ID, CORRECT_AMOUNT));
    }

    @Test
    void testTransferWithIncorrectAccountsAndBigAmount() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> transactionsService.transfer(INCORRECT_ACCOUNT_ID, INCORRECT_PAYEE_ACCOUNT_ID, INCORRECT_BIG_AMOUNT));
    }

}
