package ru.sberbank.demo.app.unit;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sberbank.demo.app.category.UnitTests;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.transaction.DepositTransactionException;
import ru.sberbank.demo.app.exception.transaction.TransferTransactionException;
import ru.sberbank.demo.app.exception.transaction.WithdrawTransactionException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.model.transactions.DepositTransaction;
import ru.sberbank.demo.app.model.transactions.TransferTransaction;
import ru.sberbank.demo.app.model.transactions.WithdrawTransaction;
import ru.sberbank.demo.app.repository.AccountsRepository;
import ru.sberbank.demo.app.repository.DepositTransactionsRepository;
import ru.sberbank.demo.app.repository.TransferTransactionsRepository;
import ru.sberbank.demo.app.repository.WithdrawTransactionsRepository;
import ru.sberbank.demo.app.service.transaction.TransactionsService;
import ru.sberbank.demo.app.service.transaction.TransactionsServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@Category(UnitTests.class)
@ExtendWith(MockitoExtension.class)
class TransactionsServiceTest {

    private static Long POSITIVE_AMOUNT = 50L;
    private static Long NEGATIVE_AMOUNT = -50L;
    private static Long ZERO_AMOUNT = 0L;

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private DepositTransactionsRepository depositTransactionsRepository;

    @Mock
    private TransferTransactionsRepository transferTransactionsRepository;

    @Mock
    private WithdrawTransactionsRepository withdrawTransactionsRepository;

    @InjectMocks
    private TransactionsService transactionsService = new TransactionsServiceImpl();

    @Test
    void depositTest() throws DepositTransactionException, AccountNotFoundException {
        Account account = new Account();
        account.setBalance(POSITIVE_AMOUNT);
        when(accountsRepository.getAccountById(anyLong())).thenReturn(Optional.of(account));
        when(depositTransactionsRepository.save(any(DepositTransaction.class))).thenReturn(new DepositTransaction());
        DepositTransaction depositTransaction = transactionsService.deposit(1L, POSITIVE_AMOUNT);
        assertEquals(depositTransaction, new DepositTransaction());
        assertEquals(account.getBalance(), 2 * POSITIVE_AMOUNT);
    }

    @Test
    void depositZeroAmountExceptionTest() {
        assertThrows(DepositTransactionException.class, () -> transactionsService.deposit(1L, ZERO_AMOUNT));
    }

    @Test
    void depositNegativeAmountExceptionTest() {
        assertThrows(DepositTransactionException.class, () -> transactionsService.deposit(1L, NEGATIVE_AMOUNT));
    }

    @Test
    void withdrawTest() throws AccountNotFoundException, WithdrawTransactionException {
        Account account = new Account();
        account.setBalance(POSITIVE_AMOUNT);
        when(accountsRepository.getAccountById(anyLong())).thenReturn(Optional.of(account));
        when(withdrawTransactionsRepository.save(any(WithdrawTransaction.class))).thenReturn(new WithdrawTransaction());
        WithdrawTransaction withdrawTransaction = transactionsService.withdraw(1L, POSITIVE_AMOUNT);
        assertEquals(withdrawTransaction, new WithdrawTransaction());
        assertEquals(account.getBalance(), 0L);
    }

    @Test
    void withdrawZeroAmountExceptionTest() {
        assertThrows(WithdrawTransactionException.class, () -> transactionsService.withdraw(1L, ZERO_AMOUNT));
    }

    @Test
    void withdrawNegativeAmountExceptionTest() {
        assertThrows(WithdrawTransactionException.class, () -> transactionsService.withdraw(1L, NEGATIVE_AMOUNT));
    }

    @Test
    void withdrawExceedsAccountBalanceExceptionTest() {
        Account account = new Account();
        account.setBalance(ZERO_AMOUNT);
        when(accountsRepository.getAccountById(anyLong())).thenReturn(Optional.of(account));
        assertThrows(WithdrawTransactionException.class, () -> transactionsService.withdraw(1L, POSITIVE_AMOUNT));
    }

    @Test
    void withdrawAccountNotFoundExceptionTest() {
        when(accountsRepository.getAccountById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> transactionsService.withdraw(1L, POSITIVE_AMOUNT));
    }

    @Test
    void transferZeroAmountExceptionTest() {
        assertThrows(TransferTransactionException.class, () -> transactionsService.transfer(1L, 2L, ZERO_AMOUNT));
    }

    @Test
    void transferSameAccountExceptionTest() {
        assertThrows(TransferTransactionException.class, () -> transactionsService.transfer(1L, 1L, POSITIVE_AMOUNT));
    }

    @Test
    void transferNegativeAmountExceptionTest() {
        assertThrows(TransferTransactionException.class, () -> transactionsService.transfer(1L, 2L, NEGATIVE_AMOUNT));
    }

    @Test
    void transferTest() throws AccountNotFoundException, TransferTransactionException {
        Account withdrawAccount = new Account();
        withdrawAccount.setBalance(POSITIVE_AMOUNT);
        withdrawAccount.setId(1L);
        Account depositAccount = new Account();
        depositAccount.setBalance(POSITIVE_AMOUNT);
        depositAccount.setId(2L);
        when(accountsRepository.getAccountById(anyLong()))
                .thenReturn(Optional.of(withdrawAccount))
                .thenReturn(Optional.of(depositAccount));
        when(transferTransactionsRepository.save(any(TransferTransaction.class))).thenReturn(new TransferTransaction());
        TransferTransaction transferTransaction = transactionsService.transfer(withdrawAccount.getId(), depositAccount.getId(), POSITIVE_AMOUNT);
        assertEquals(transferTransaction, new TransferTransaction());
        assertEquals(withdrawAccount.getBalance(), 0L);
        assertEquals(depositAccount.getBalance(), 100L);
    }

    @Test
    void transferExceedAmountExceptionTest() {
        Account withdrawAccount = new Account();
        withdrawAccount.setBalance(POSITIVE_AMOUNT);
        Account depositAccount = new Account();
        depositAccount.setBalance(POSITIVE_AMOUNT);
        when(accountsRepository.getAccountById(anyLong()))
                .thenReturn(Optional.of(withdrawAccount))
                .thenReturn(Optional.of(depositAccount));
        assertThrows(TransferTransactionException.class, () -> transactionsService.transfer(1L, 2L, 2 * POSITIVE_AMOUNT));
    }

}