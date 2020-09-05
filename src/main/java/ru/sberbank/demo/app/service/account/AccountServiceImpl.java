package ru.sberbank.demo.app.service.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.repository.AccountRepository;
import ru.sberbank.demo.app.repository.ClientRepository;
import ru.sberbank.demo.app.service.AbstractPaginatedService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AccountServiceImpl extends AbstractPaginatedService<Account, AccountNotFoundException> implements AccountService {

    private AccountRepository accountRepository;

    private ClientRepository clientRepository;

    public AccountServiceImpl() {
        super(AccountNotFoundException.class);
    }

    @Autowired
    public void setAccountRepository(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setClientRepository(final ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Получение списка всех счетов клиента
     *
     * @param clientId идентификатор клиента
     * @return список счетов клиента
     * @throws ResourceNotFoundException если клиент с заданным идентификатором не найден
     */
    @Override
    @Transactional(readOnly = true)
    public List<Account> getClientAccounts(final UUID clientId) throws ResourceNotFoundException {
        findOne(clientId);
        Optional<List<Account>> accountsByClientId = accountRepository.getAccountsByClientId(clientId);
        return accountsByClientId.orElseGet(ArrayList::new);
    }

    @Override
    protected PagingAndSortingRepository<Account, UUID> getDao() {
        return accountRepository;
    }

}
