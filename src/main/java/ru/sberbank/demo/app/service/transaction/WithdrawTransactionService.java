package ru.sberbank.demo.app.service.transaction;

import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.transaction.WithdrawTransactionException;
import ru.sberbank.demo.app.model.transaction.WithdrawTransaction;

public interface WithdrawTransactionService {

    WithdrawTransaction withdraw(final Long accountId, final Long transferAmount) throws AccountNotFoundException, WithdrawTransactionException;

}
