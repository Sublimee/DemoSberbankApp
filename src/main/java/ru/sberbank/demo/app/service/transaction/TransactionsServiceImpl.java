package ru.sberbank.demo.app.service.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class TransactionsServiceImpl implements TransactionsService {

    private AccountsRepository accountsRepository;
    private DepositTransactionsRepository depositTransactionsRepository;
    private TransferTransactionsRepository transferTransactionsRepository;
    private WithdrawTransactionsRepository withdrawTransactionsRepository;

    @Autowired
    public void setAccountsRepository(final AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Autowired
    public void setDepositTransactionsRepository(final DepositTransactionsRepository depositTransactionsRepository) {
        this.depositTransactionsRepository = depositTransactionsRepository;
    }

    @Autowired
    public void setTransferTransactionsRepository(final TransferTransactionsRepository transferTransactionsRepository) {
        this.transferTransactionsRepository = transferTransactionsRepository;
    }

    @Autowired
    public void setWithdrawTransactionsRepository(final WithdrawTransactionsRepository withdrawTransactionsRepository) {
        this.withdrawTransactionsRepository = withdrawTransactionsRepository;
    }

    /**
     * Пополнение счета
     *
     * @param accountId     идентификатор счета клиента
     * @param depositAmount сумма пополнения счета
     * @return информация о выполненной транзакции
     * @throws AccountNotFoundException    если счет с заданным идентификатором не найден
     * @throws DepositTransactionException если задана некорректная сумма пополнения
     */
    @Override
    @Transactional
    public DepositTransaction deposit(final Long accountId, final Long depositAmount) throws AccountNotFoundException, DepositTransactionException {
        checkDepositTransactionParams(depositAmount);
        return getDepositTransaction(depositAmount, getDepositAccount(accountId, depositAmount));
    }

    /**
     * Снятие денег со счета
     *
     * @param accountId      идентификатор счета клиента
     * @param withdrawAmount сумма снятия со счета
     * @return информация о выполненной транзакции
     * @throws AccountNotFoundException     если счет с заданным идентификатором не найден
     * @throws WithdrawTransactionException если задана некорректная сумма для снятия
     */
    @Override
    @Transactional
    public WithdrawTransaction withdraw(final Long accountId, final Long withdrawAmount) throws AccountNotFoundException, WithdrawTransactionException {
        checkWithdrawTransactionParams(withdrawAmount);
        return getWithdrawTransaction(withdrawAmount, getWithdrawAccount(accountId, withdrawAmount));
    }

    /**
     * Перевод денег со счета на счет
     *
     * @param fromAccountId  идентификатор счета отправителя
     * @param toAccountId    идентификатор счета получателя
     * @param transferAmount сумма перевода
     * @return информация о выполненной транзакции
     * @throws TransferTransactionException если задана некорректная сумма для перевода
     * @throws AccountNotFoundException     если один из заданных счетов не найден
     */
    @Override
    @Transactional
    public TransferTransaction transfer(final Long fromAccountId, final Long toAccountId, final Long transferAmount) throws TransferTransactionException, AccountNotFoundException {
        checkTransferTransactionParams(fromAccountId, toAccountId, transferAmount);
        return getTransferTransaction(fromAccountId, toAccountId, transferAmount);
    }

    private Account getAccount(final Long accountId) throws AccountNotFoundException {
        Optional<Account> accountById = accountsRepository.getAccountById(accountId);
        if (!accountById.isPresent()) {
            log.error("Счет с идентификатором {} не найден", accountId);
            throw new AccountNotFoundException("Счет с идентификатором" + accountId + "не найден");
        }
        return accountById.get();
    }

    private Account getWithdrawAccount(final Long accountId, final Long withdrawAmount) throws AccountNotFoundException, WithdrawTransactionException {
        Account account = getAccount(accountId);
        if (account.getBalance() < withdrawAmount) {
            log.error("Имеющейся на счете {} суммы недостаточно для завершения операции.", accountId);
            throw new WithdrawTransactionException("Имеющейся на счете " + accountId + " суммы недостаточно для завершения операции");
        }
        account.setBalance(account.getBalance() - withdrawAmount);
        return account;
    }

    private Account getDepositAccount(final Long accountId, final Long transferAmount) throws AccountNotFoundException {
        Account account = getAccount(accountId);
        account.setBalance(account.getBalance() + transferAmount);
        return account;
    }

    private DepositTransaction getDepositTransaction(final Long transferAmount, final Account account) {
        DepositTransaction depositTransaction = new DepositTransaction();
        depositTransaction.setAccount(account);
        depositTransaction.setTransferAmount(transferAmount);
        return depositTransactionsRepository.save(depositTransaction);
    }

    private WithdrawTransaction getWithdrawTransaction(final Long withdrawAmount, final Account account) {
        WithdrawTransaction withdrawTransaction = new WithdrawTransaction();
        withdrawTransaction.setAccount(account);
        withdrawTransaction.setTransferAmount(withdrawAmount);
        return withdrawTransactionsRepository.save(withdrawTransaction);
    }

    private TransferTransaction getTransferTransaction(final Long fromAccountId, final Long toAccountId, final Long transferAmount) throws AccountNotFoundException, TransferTransactionException {
        TransferTransaction transferTransaction = new TransferTransaction();
        try {
            transferTransaction.setAccount(getWithdrawAccount(fromAccountId, transferAmount));
        } catch (WithdrawTransactionException e) {
            log.error(e.getMessage());
            throw new TransferTransactionException(e.getMessage());
        }
        transferTransaction.setPayee(getDepositAccount(toAccountId, transferAmount));
        transferTransaction.setTransferAmount(transferAmount);
        return transferTransactionsRepository.save(transferTransaction);
    }

    private void checkTransferTransactionParams(final Long fromAccountId, final Long toAccountId, final Long transferAmount) throws TransferTransactionException {
        if (fromAccountId.equals(toAccountId)) {
            log.error("Переводы не осуществляются внутри одного счета: {}", fromAccountId);
            throw new TransferTransactionException("Переводы не осуществляются внутри одного счета");
        }
        if (transferAmount < 0) {
            log.error("Сумма перевода не может быть задана отрицательным числом: {}", transferAmount);
            throw new TransferTransactionException("Сумма перевода не может быть задана отрицательным числом: " + transferAmount);
        }
        if (transferAmount == 0) {
            log.error("Перевод возможен от суммы в 1 у.е. Задано: 0 у.е.");
            throw new TransferTransactionException("Перевод возможен от суммы в 1 у.е. Задано: 0 у.е.");
        }
    }

    private void checkWithdrawTransactionParams(final Long withdrawAmount) throws WithdrawTransactionException {
        if (withdrawAmount < 0) {
            log.error("Сумма снятия не может быть задана отрицательным числом: {}", withdrawAmount);
            throw new WithdrawTransactionException("Сумма снятия не может быть задана отрицательным числом: " + withdrawAmount);
        }
        if (withdrawAmount == 0) {
            log.error("Снятие возможно от суммы в 1 у.е. Задано: 0 у.е.");
            throw new WithdrawTransactionException("Снятие возможно от суммы в 1 у.е. Задано: 0 у.е.");
        }
    }

    private void checkDepositTransactionParams(final Long depositAmount) throws DepositTransactionException {
        if (depositAmount < 0) {
            log.error("Сумма пополнения не может быть задана отрицательным числом: {}", depositAmount);
            throw new DepositTransactionException("Сумма пополнения не может быть задана отрицательным числом: " + depositAmount);
        }
        if (depositAmount == 0) {
            log.error("Пополнение возможно на сумму от 1 у.е. Задано: 0 у.е.");
            throw new DepositTransactionException("Пополнение возможно на сумму от 1 у.е. Задано: 0 у.е.");
        }
    }

}
