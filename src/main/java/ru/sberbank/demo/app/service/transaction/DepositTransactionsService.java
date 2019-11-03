package ru.sberbank.demo.app.service.transaction;

import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.transaction.DepositTransactionException;
import ru.sberbank.demo.app.model.transactions.DepositTransaction;

public interface DepositTransactionsService {

    DepositTransaction deposit(Long accountId, Long transferAmount) throws AccountNotFoundException, DepositTransactionException;

}
