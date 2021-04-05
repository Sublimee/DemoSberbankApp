package ru.sberbank.demo.app.service.transaction.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.exception.transaction.DepositTransactionException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.model.transaction.DepositTransaction;
import ru.sberbank.demo.app.repository.DepositTransactionRepository;
import ru.sberbank.demo.app.service.account.AccountService;
import ru.sberbank.demo.app.service.transaction.DepositTransactionService;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.UUID;

@Service
public class DepositServiceImpl implements DepositTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final AccountService accountService;
    private final DepositTransactionRepository depositTransactionRepository;

    @Autowired
    public DepositServiceImpl(AccountService accountService, DepositTransactionRepository depositTransactionRepository) {
        this.accountService = accountService;
        this.depositTransactionRepository = depositTransactionRepository;
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

    @Override
    public Account getDepositAccount(final UUID accountId, final Long transferAmount) throws ResourceNotFoundException {
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

    private void checkDepositTransactionParams(final Long depositAmount) throws DepositTransactionException {
        if (depositAmount < 0) {
            logger.error("Сумма пополнения не может быть задана отрицательным числом: " + depositAmount);
            throw new DepositTransactionException("Сумма пополнения не может быть задана отрицательным числом: " + depositAmount);
        }
        if (depositAmount == 0) {
            logger.error("Пополнение возможно на сумму от 1 у.е. Задано: 0 у.е.");
            throw new DepositTransactionException("Пополнение возможно на сумму от 1 у.е. Задано: 0 у.е.");
        }
    }

}
