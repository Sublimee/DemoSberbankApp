package ru.sberbank.demo.app.service.account;

import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.model.Account;

import java.util.List;

public interface AccountService {

    List<Account> getClientAccounts(final Long clientId) throws ClientNotFoundException, AccountNotFoundException;

    Account getAccountById(final Long accountId) throws AccountNotFoundException;

}
