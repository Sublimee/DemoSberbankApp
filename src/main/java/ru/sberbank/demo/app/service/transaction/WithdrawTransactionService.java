package ru.sberbank.demo.app.service.transaction;

import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.model.transaction.WithdrawTransaction;

import java.util.UUID;

public interface WithdrawTransactionService {

    WithdrawTransaction withdraw(final UUID accountId, final Long transferAmount) throws ResourceNotFoundException;

    Account getWithdrawAccount(UUID accountId, Long withdrawAmount) throws ResourceNotFoundException;

}
