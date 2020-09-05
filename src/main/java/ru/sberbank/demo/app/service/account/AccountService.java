package ru.sberbank.demo.app.service.account;


import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.service.IPaginatedService;

import java.util.List;
import java.util.UUID;

public interface AccountService extends IPaginatedService<Account> {

    List<Account> getClientAccounts(final UUID clientId) throws ResourceNotFoundException;

}
