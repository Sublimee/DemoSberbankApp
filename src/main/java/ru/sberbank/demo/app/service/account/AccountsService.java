package ru.sberbank.demo.app.service.account;

import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.model.Account;

import java.util.List;

public interface AccountsService {

    List<Account> getClientAccounts(Long clientId) throws ClientNotFoundException;

    Account getAccountById(Long accountId) throws AccountNotFoundException;

}
