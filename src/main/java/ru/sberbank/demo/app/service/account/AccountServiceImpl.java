package ru.sberbank.demo.app.service.account;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.repository.AccountRepository;
import ru.sberbank.demo.app.service.AbstractPaginatedService;
import ru.sberbank.demo.app.service.client.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl extends AbstractPaginatedService<Account, AccountNotFoundException> implements AccountService {

    private final AccountRepository accountRepository;

    private final ClientService clientService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, ClientService clientService) {
        super(AccountNotFoundException.class);
        this.accountRepository = accountRepository;
        this.clientService = clientService;
    }


    /**
     * Получение списка всех счетов клиента
     *
     * @param clientId идентификатор клиента
     * @return список счетов клиента
     */
    @Override
    @Transactional(readOnly = true)
    public List<Account> getClientAccounts(final UUID clientId) throws ResourceNotFoundException {
        clientService.findOne(clientId);
        Optional<List<Account>> accountsByClientId = accountRepository.getAccountsByClientId(clientId);
        return accountsByClientId.orElseGet(ArrayList::new);
    }

    @Override
    protected PagingAndSortingRepository<Account, UUID> getDao() {
        return accountRepository;
    }

}
