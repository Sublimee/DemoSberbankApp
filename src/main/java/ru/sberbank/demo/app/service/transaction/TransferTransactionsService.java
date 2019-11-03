package ru.sberbank.demo.app.service.transaction;

import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.transaction.TransferTransactionException;
import ru.sberbank.demo.app.model.transactions.TransferTransaction;

public interface TransferTransactionsService {

    TransferTransaction transfer(Long fromAccountId, Long toAccountId, Long transferAmount) throws TransferTransactionException, AccountNotFoundException;

}
