package ru.sberbank.demo.app.service.transaction;

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

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private final AccountsRepository accountsRepository;
    private final DepositTransactionsRepository depositTransactionsRepository;
    private final TransferTransactionsRepository transferTransactionsRepository;
    private final WithdrawTransactionsRepository withdrawTransactionsRepository;

    public TransactionsServiceImpl(AccountsRepository accountsRepository, DepositTransactionsRepository depositTransactionsRepository, TransferTransactionsRepository transferTransactionsRepository, WithdrawTransactionsRepository withdrawTransactionsRepository) {
        this.accountsRepository = accountsRepository;
        this.depositTransactionsRepository = depositTransactionsRepository;
        this.transferTransactionsRepository = transferTransactionsRepository;
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
    public DepositTransaction deposit(Long accountId, Long depositAmount) throws AccountNotFoundException, DepositTransactionException {
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
    public WithdrawTransaction withdraw(Long accountId, Long withdrawAmount) throws AccountNotFoundException, WithdrawTransactionException {
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
    public TransferTransaction transfer(Long fromAccountId, Long toAccountId, Long transferAmount) throws TransferTransactionException, AccountNotFoundException {
        checkTransferTransactionParams(fromAccountId, toAccountId, transferAmount);
        return getTransferTransaction(fromAccountId, toAccountId, transferAmount);
    }

    private Account getWithdrawAccount(Long accountId, Long withdrawAmount) throws AccountNotFoundException, WithdrawTransactionException {
        Account account = accountsRepository.getAccountById(accountId).orElseThrow(AccountNotFoundException::new);
        if (account.getBalance() < withdrawAmount) {
            throw new WithdrawTransactionException("Имеющейся на счете суммы недостаточно для завершения операции");
        }
        account.setBalance(account.getBalance() - withdrawAmount);
        return account;
    }

    private Account getDepositAccount(Long accountId, Long transferAmount) throws AccountNotFoundException {
        Account account = accountsRepository.getAccountById(accountId).orElseThrow(AccountNotFoundException::new);
        account.setBalance(account.getBalance() + transferAmount);
        return account;
    }

    private DepositTransaction getDepositTransaction(Long transferAmount, Account account) {
        DepositTransaction depositTransaction = new DepositTransaction();
        depositTransaction.setAccount(account);
        depositTransaction.setTransferAmount(transferAmount);
        return depositTransactionsRepository.save(depositTransaction);
    }

    private TransferTransaction getTransferTransaction(Long fromAccountId, Long toAccountId, Long transferAmount) throws AccountNotFoundException, TransferTransactionException {
        TransferTransaction transferTransaction = new TransferTransaction();
        try {
            transferTransaction.setAccount(getWithdrawAccount(fromAccountId, transferAmount));
        } catch (WithdrawTransactionException e) {
            throw new TransferTransactionException(e.getMessage());
        }
        transferTransaction.setPayee(getDepositAccount(toAccountId, transferAmount));
        transferTransaction.setTransferAmount(transferAmount);
        return transferTransactionsRepository.save(transferTransaction);
    }

    private WithdrawTransaction getWithdrawTransaction(Long withdrawAmount, Account account) {
        WithdrawTransaction withdrawTransaction = new WithdrawTransaction();
        withdrawTransaction.setAccount(account);
        withdrawTransaction.setTransferAmount(withdrawAmount);
        return withdrawTransactionsRepository.save(withdrawTransaction);
    }

    private void checkTransferTransactionParams(Long fromAccountId, Long toAccountId, Long transferAmount) throws TransferTransactionException {
        if (fromAccountId.equals(toAccountId)) {
            throw new TransferTransactionException("Переводы не осуществляются внутри одного счета");
        }
        if (transferAmount < 0) {
            throw new TransferTransactionException("Сумма перевода не может быть задана отрицательным числом: " + transferAmount);
        }
        if (transferAmount == 0) {
            throw new TransferTransactionException("Перевод возможен от суммы в 1 у.е. Задано: 0 у.е.");
        }
    }

    private void checkWithdrawTransactionParams(Long withdrawAmount) throws WithdrawTransactionException {
        if (withdrawAmount < 0) {
            throw new WithdrawTransactionException("Сумма снятия не может быть задана отрицательным числом: " + withdrawAmount);
        }
        if (withdrawAmount == 0) {
            throw new WithdrawTransactionException("Снятие возможно от суммы в 1 у.е. Задано: 0 у.е.");
        }
    }

    private void checkDepositTransactionParams(Long transferAmount) throws DepositTransactionException {
        if (transferAmount < 0) {
            throw new DepositTransactionException("Сумма пополнения не может быть задана отрицательным числом: " + transferAmount);
        }
        if (transferAmount == 0) {
            throw new DepositTransactionException("Пополнение возможно на сумму от 1 у.е. Задано: 0 у.е.");
        }
    }

}
