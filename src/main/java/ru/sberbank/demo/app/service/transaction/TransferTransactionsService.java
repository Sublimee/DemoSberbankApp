package ru.sberbank.demo.app.service.transaction;

import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.transaction.TransferTransactionException;
import ru.sberbank.demo.app.model.transactions.TransferTransaction;

public interface TransferTransactionsService {

    TransferTransaction transfer(final Long fromAccountId, final Long toAccountId, final Long transferAmount) throws TransferTransactionException, AccountNotFoundException;

}
