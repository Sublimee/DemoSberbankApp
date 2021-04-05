package ru.sberbank.demo.app.service.transaction;

import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.model.transaction.DepositTransaction;

import java.util.UUID;

public interface DepositTransactionService {

    DepositTransaction deposit(final UUID accountId, final Long transferAmount) throws ResourceNotFoundException;

    Account getDepositAccount(UUID accountId, Long transferAmount) throws ResourceNotFoundException;

}
