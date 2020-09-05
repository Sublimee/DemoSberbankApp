package ru.sberbank.demo.app.service.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.exception.transaction.DepositTransactionException;
import ru.sberbank.demo.app.exception.transaction.TransferTransactionException;
import ru.sberbank.demo.app.exception.transaction.WithdrawTransactionException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.model.transaction.DepositTransaction;
import ru.sberbank.demo.app.model.transaction.TransferTransaction;
import ru.sberbank.demo.app.model.transaction.WithdrawTransaction;
import ru.sberbank.demo.app.repository.DepositTransactionRepository;
import ru.sberbank.demo.app.repository.TransferTransactionRepository;
import ru.sberbank.demo.app.repository.WithdrawTransactionRepository;
import ru.sberbank.demo.app.service.account.AccountService;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private AccountService accountService;
    private DepositTransactionRepository depositTransactionRepository;
    private TransferTransactionRepository transferTransactionRepository;
    private WithdrawTransactionRepository withdrawTransactionRepository;


    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setDepositTransactionRepository(final DepositTransactionRepository depositTransactionRepository) {
        this.depositTransactionRepository = depositTransactionRepository;
    }

    @Autowired
    public void setTransferTransactionRepository(final TransferTransactionRepository transferTransactionRepository) {
        this.transferTransactionRepository = transferTransactionRepository;
    }

    @Autowired
    public void setWithdrawTransactionRepository(final WithdrawTransactionRepository withdrawTransactionRepository) {
        this.withdrawTransactionRepository = withdrawTransactionRepository;
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
    public DepositTransaction deposit(final UUID accountId, final Long depositAmount) throws ResourceNotFoundException {
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
    public WithdrawTransaction withdraw(final UUID accountId, final Long withdrawAmount) throws ResourceNotFoundException {
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
    public TransferTransaction transfer(final UUID fromAccountId, final UUID toAccountId, final Long transferAmount) throws ResourceNotFoundException {
        checkTransferTransactionParams(fromAccountId, toAccountId, transferAmount);
        return getTransferTransaction(fromAccountId, toAccountId, transferAmount);
    }

    private Account getWithdrawAccount(final UUID accountId, final Long withdrawAmount) throws ResourceNotFoundException {
        Account account = accountService.findOne(accountId);
        if (account.getBalance() < withdrawAmount) {
            log.error("Имеющейся на счете " + accountId + " суммы недостаточно для завершения операции.");
            throw new WithdrawTransactionException("Имеющейся на счете " + accountId + " суммы недостаточно для завершения операции");
        }
        account.setBalance(account.getBalance() - withdrawAmount);
        return account;
    }

    private Account getDepositAccount(final UUID accountId, final Long transferAmount) throws ResourceNotFoundException {
        Account account = accountService.findOne(accountId);
        account.setBalance(account.getBalance() + transferAmount);
        return account;
    }

    private DepositTransaction getDepositTransaction(final Long transferAmount, final Account account) {
        DepositTransaction depositTransaction = new DepositTransaction();
        depositTransaction.setAccount(account);
        depositTransaction.setTransferAmount(transferAmount);
        return depositTransactionRepository.save(depositTransaction);
    }

    private WithdrawTransaction getWithdrawTransaction(final Long withdrawAmount, final Account account) {
        WithdrawTransaction withdrawTransaction = new WithdrawTransaction();
        withdrawTransaction.setAccount(account);
        withdrawTransaction.setTransferAmount(withdrawAmount);
        return withdrawTransactionRepository.save(withdrawTransaction);
    }

    private TransferTransaction getTransferTransaction(final UUID fromAccountId, final UUID toAccountId, final Long transferAmount) throws ResourceNotFoundException {
        TransferTransaction transferTransaction = new TransferTransaction();
        try {
            transferTransaction.setAccount(getWithdrawAccount(fromAccountId, transferAmount));
        } catch (WithdrawTransactionException e) {
            log.error(e.getMessage());
            throw new TransferTransactionException(e.getMessage());
        }
        transferTransaction.setPayee(getDepositAccount(toAccountId, transferAmount));
        transferTransaction.setTransferAmount(transferAmount);
        return transferTransactionRepository.save(transferTransaction);
    }

    private void checkTransferTransactionParams(final UUID fromAccountId, final UUID toAccountId, final Long transferAmount) throws TransferTransactionException {
        if (fromAccountId.equals(toAccountId)) {
            log.error("Переводы не осуществляются внутри одного счета: " + fromAccountId);
            throw new TransferTransactionException("Переводы не осуществляются внутри одного счета");
        }
        if (transferAmount < 0) {
            log.error("Сумма перевода не может быть задана отрицательным числом: " + transferAmount);
            throw new TransferTransactionException("Сумма перевода не может быть задана отрицательным числом: " + transferAmount);
        }
        if (transferAmount == 0) {
            log.error("Перевод возможен от суммы в 1 у.е. Задано: 0 у.е.");
            throw new TransferTransactionException("Перевод возможен от суммы в 1 у.е. Задано: 0 у.е.");
        }
    }

    private void checkWithdrawTransactionParams(final Long withdrawAmount) throws WithdrawTransactionException {
        if (withdrawAmount < 0) {
            log.error("Сумма снятия не может быть задана отрицательным числом: " + withdrawAmount);
            throw new WithdrawTransactionException("Сумма снятия не может быть задана отрицательным числом: " + withdrawAmount);
        }
        if (withdrawAmount == 0) {
            log.error("Снятие возможно от суммы в 1 у.е. Задано: 0 у.е.");
            throw new WithdrawTransactionException("Снятие возможно от суммы в 1 у.е. Задано: 0 у.е.");
        }
    }

    private void checkDepositTransactionParams(final Long depositAmount) throws DepositTransactionException {
        if (depositAmount < 0) {
            log.error("Сумма пополнения не может быть задана отрицательным числом: " + depositAmount);
            throw new DepositTransactionException("Сумма пополнения не может быть задана отрицательным числом: " + depositAmount);
        }
        if (depositAmount == 0) {
            log.error("Пополнение возможно на сумму от 1 у.е. Задано: 0 у.е.");
            throw new DepositTransactionException("Пополнение возможно на сумму от 1 у.е. Задано: 0 у.е.");
        }
    }

}
