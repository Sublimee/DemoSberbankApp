package ru.sberbank.demo.app.service.account;

import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.service.IPaginatedService;

import java.util.List;

public interface AccountService extends IPaginatedService<Account> {

    List<Account> getClientAccounts(final Long clientId) throws ClientNotFoundException, AccountNotFoundException;

}
