package ru.sberbank.demo.app.service.unit;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

@Tag("UnitTests")
@ExtendWith(MockitoExtension.class)
class TransactionsServiceTest {

    private static final Long POSITIVE_AMOUNT = 50L;
    private static final Long NEGATIVE_AMOUNT = -50L;
    private static final Long ZERO_AMOUNT = 0L;

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

    /*
        Шаг 1: Определяем свойства корректности

        0. Транзакции должны выполняться с существующими счетами.
        1. Баланс счета не может стать отрицательным
        2. Баланс счета не может переполниться.
        3. Нельзя выполнять транзакции с отрицательной или нулевой суммой.
        4. Переводы внутри одного счета запрещены.
        5. Баланс должен точно соответствовать результату выполненной транзакции.
        6. Последовательности операций должны быть консистентны.
            Результат операций (пополнение, снятие, перевод) должен быть консистентным при выполнении в произвольной последовательности.

        Шаг 2: Выбор способа тестирования

        0. Модульные тесты
        1. Модульные тесты, обзор кода
        2. Модульные тесты, обзор кода
        3. Модульные тесты
        4. Модульные тесты
        5. Модульные тесты, фазз-тестирование
        6. Фазз-тестирование
     */

    /*
        Проверка свойства 0
     */
    @Test
    void depositAccountNotFoundExceptionTest() {
        when(accountsRepository.getAccountById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> transactionsService.deposit(1L, POSITIVE_AMOUNT));
    }

    /*
        Проверка свойства 5
     */
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

    /*
        Проверка свойства 3
     */
    @Test
    void depositZeroAmountExceptionTest() {
        assertThrows(DepositTransactionException.class, () -> transactionsService.deposit(1L, ZERO_AMOUNT));
    }

    /*
        Проверка свойства 3
     */
    @Test
    void depositNegativeAmountExceptionTest() {
        assertThrows(DepositTransactionException.class, () -> transactionsService.deposit(1L, NEGATIVE_AMOUNT));
    }

    /*
        Проверка свойства 2
     */
    @Test
    void depositOverflowTest() {
        Long accountId = 1L;
        Long depositAmount = Long.MAX_VALUE;

        Account account = new Account();
        account.setId(accountId);
        account.setBalance(Long.MAX_VALUE - 1);
        when(accountsRepository.getAccountById(accountId)).thenReturn(Optional.of(account));

        assertThrows(DepositTransactionException.class,
                () -> transactionsService.deposit(accountId, depositAmount),
                "Пополнение счета должно выбрасывать исключение при переполнении баланса");
    }

    /*
        Проверка свойства 5
     */
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

    /*
        Проверка свойства 3
     */
    @Test
    void withdrawZeroAmountExceptionTest() {
        assertThrows(WithdrawTransactionException.class, () -> transactionsService.withdraw(1L, ZERO_AMOUNT));
    }

    /*
        Проверка свойства 3
     */
    @Test
    void withdrawNegativeAmountExceptionTest() {
        assertThrows(WithdrawTransactionException.class, () -> transactionsService.withdraw(1L, NEGATIVE_AMOUNT));
    }

    /*
        Проверка свойства 1
     */
    @Test
    void withdrawExceedsAccountBalanceExceptionTest() {
        Account account = new Account();
        account.setBalance(ZERO_AMOUNT);
        when(accountsRepository.getAccountById(anyLong())).thenReturn(Optional.of(account));
        assertThrows(WithdrawTransactionException.class, () -> transactionsService.withdraw(1L, POSITIVE_AMOUNT));
    }

    /*
        Проверка свойства 0
     */
    @Test
    void withdrawAccountNotFoundExceptionTest() {
        when(accountsRepository.getAccountById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> transactionsService.withdraw(1L, POSITIVE_AMOUNT));
    }

    /*
        Проверка свойства 0
     */
    @Test
    void toTransferAccountNotFoundExceptionTest() {
        long fromAccountId = 1L;
        long toAccountId = 2L;
        Account fromAccount = new Account();
        fromAccount.setBalance(POSITIVE_AMOUNT);
        fromAccount.setId(fromAccountId);
        when(accountsRepository.getAccountById(fromAccountId)).thenReturn(Optional.of(fromAccount));
        when(accountsRepository.getAccountById(toAccountId)).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> transactionsService.transfer(fromAccountId, toAccountId, POSITIVE_AMOUNT));
    }

    /*
        Проверка свойства 0
     */
    @Test
    void fromTransferAccountNotFoundExceptionTest() {
        long fromAccountId = 1L;
        long toAccountId = 2L;
        when(accountsRepository.getAccountById(fromAccountId)).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> transactionsService.transfer(fromAccountId, toAccountId, POSITIVE_AMOUNT));
    }

    /*
        Проверка свойства 3
     */
    @Test
    void transferZeroAmountExceptionTest() {
        assertThrows(TransferTransactionException.class, () -> transactionsService.transfer(1L, 2L, ZERO_AMOUNT));
    }

    /*
        Проверка свойства 4
     */
    @Test
    void transferSameAccountExceptionTest() {
        assertThrows(TransferTransactionException.class, () -> transactionsService.transfer(1L, 1L, POSITIVE_AMOUNT));
    }

    /*
        Проверка свойства 3
     */
    @Test
    void transferNegativeAmountExceptionTest() {
        assertThrows(TransferTransactionException.class, () -> transactionsService.transfer(1L, 2L, NEGATIVE_AMOUNT));
    }

    /*
        Проверка свойства 5
     */
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

    /*
        Проверка свойства 1
     */
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

    /*
        Проверка свойства 2
     */
    @Test
    void transferOverflowTest() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        Long transferAmount = Long.MAX_VALUE;

        Account fromAccount = new Account();
        fromAccount.setBalance(POSITIVE_AMOUNT);
        fromAccount.setId(1L);

        when(accountsRepository.getAccountById(fromAccountId)).thenReturn(Optional.of(fromAccount));

        assertThrows(TransferTransactionException.class,
                () -> transactionsService.transfer(fromAccountId, toAccountId, transferAmount));
    }
}