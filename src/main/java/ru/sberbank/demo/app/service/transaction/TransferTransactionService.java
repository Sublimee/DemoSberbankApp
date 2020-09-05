package ru.sberbank.demo.app.service.transaction;

import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.model.transaction.TransferTransaction;

import java.util.UUID;

public interface TransferTransactionService {

    TransferTransaction transfer(final UUID fromAccountId, final UUID toAccountId, final Long transferAmount) throws ResourceNotFoundException;

}
