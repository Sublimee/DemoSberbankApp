package ru.sberbank.demo.app.service.transaction;

import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.transaction.WithdrawTransactionException;
import ru.sberbank.demo.app.model.transactions.WithdrawTransaction;

public interface WithdrawTransactionsService {

    WithdrawTransaction withdraw(final Long accountId, final Long transferAmount) throws AccountNotFoundException, WithdrawTransactionException;

}
