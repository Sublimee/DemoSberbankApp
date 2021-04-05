package ru.sberbank.demo.app.service.transaction.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.exception.transaction.WithdrawTransactionException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.model.transaction.WithdrawTransaction;
import ru.sberbank.demo.app.repository.WithdrawTransactionRepository;
import ru.sberbank.demo.app.service.account.AccountService;
import ru.sberbank.demo.app.service.transaction.WithdrawTransactionService;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.UUID;

@Service
public class WithdrawServiceImpl implements WithdrawTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final AccountService accountService;
    private final WithdrawTransactionRepository withdrawTransactionRepository;

    @Autowired
    public WithdrawServiceImpl(AccountService accountService, WithdrawTransactionRepository withdrawTransactionRepository) {
        this.accountService = accountService;
        this.withdrawTransactionRepository = withdrawTransactionRepository;
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
        checkTransactionAmount(withdrawAmount);
        return provideTransaction(withdrawAmount, getWithdrawAccount(accountId, withdrawAmount));
    }

    @Override
    public Account getWithdrawAccount(final UUID accountId, final Long withdrawAmount) throws ResourceNotFoundException {
        Account account = accountService.findOne(accountId);
        if (account.getBalance() < withdrawAmount) {
            logger.error("Имеющейся на счете " + accountId + " суммы недостаточно для завершения операции.");
            throw new WithdrawTransactionException("Имеющейся на счете " + accountId + " суммы недостаточно для завершения операции");
        }
        account.setBalance(account.getBalance() - withdrawAmount);
        return account;
    }

    private WithdrawTransaction provideTransaction(final Long withdrawAmount, final Account account) {
        WithdrawTransaction withdrawTransaction = new WithdrawTransaction();
        withdrawTransaction.setAccount(account);
        withdrawTransaction.setTransferAmount(withdrawAmount);
        return withdrawTransactionRepository.save(withdrawTransaction);
    }

    private void checkTransactionAmount(final Long withdrawAmount) throws WithdrawTransactionException {
        if (withdrawAmount < 0) {
            logger.error("Сумма снятия не может быть задана отрицательным числом: " + withdrawAmount);
            throw new WithdrawTransactionException("Сумма снятия не может быть задана отрицательным числом: " + withdrawAmount);
        }
        if (withdrawAmount == 0) {
            logger.error("Снятие возможно от суммы в 1 у.е. Задано: 0 у.е.");
            throw new WithdrawTransactionException("Снятие возможно от суммы в 1 у.е. Задано: 0 у.е.");
        }
    }

}
