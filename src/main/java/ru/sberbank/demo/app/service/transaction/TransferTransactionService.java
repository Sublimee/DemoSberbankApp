package ru.sberbank.demo.app.service.transaction;

import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.transaction.TransferTransactionException;
import ru.sberbank.demo.app.model.transaction.TransferTransaction;

public interface TransferTransactionService {

    TransferTransaction transfer(final Long fromAccountId, final Long toAccountId, final Long transferAmount) throws TransferTransactionException, AccountNotFoundException;

}
