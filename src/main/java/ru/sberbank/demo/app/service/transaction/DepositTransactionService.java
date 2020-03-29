package ru.sberbank.demo.app.service.transaction;

import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.transaction.DepositTransactionException;
import ru.sberbank.demo.app.model.transaction.DepositTransaction;

public interface DepositTransactionService {

    DepositTransaction deposit(final Long accountId, final Long transferAmount) throws AccountNotFoundException, DepositTransactionException;

}
