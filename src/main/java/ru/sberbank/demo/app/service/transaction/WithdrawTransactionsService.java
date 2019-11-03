package ru.sberbank.demo.app.service.transaction;

import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.transaction.WithdrawTransactionException;
import ru.sberbank.demo.app.model.transactions.WithdrawTransaction;

public interface WithdrawTransactionsService {

    WithdrawTransaction withdraw(Long accountId, Long transferAmount) throws AccountNotFoundException, WithdrawTransactionException;

}
