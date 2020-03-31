package ru.sberbank.demo.app.service.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.model.Client;
import ru.sberbank.demo.app.repository.AccountRepository;
import ru.sberbank.demo.app.repository.ClientRepository;
import ru.sberbank.demo.app.service.AbstractPaginatedService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl extends AbstractPaginatedService<Account> implements AccountService {

    private AccountRepository accountRepository;

    private ClientRepository clientRepository;

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
     * @throws ClientNotFoundException если клиент с заданным идентификатором не найден
     */
    @Override
    @Transactional(readOnly = true)
    public List<Account> getClientAccounts(final Long clientId) throws ClientNotFoundException {
        Optional<Client> client = clientRepository.findById(clientId);
        if (!client.isPresent()) {
            log.error("Клиент с идентификатором " + clientId + " не найден");
            throw new ClientNotFoundException("Клиент с идентификатором " + clientId + " не найден");
        }
        Optional<List<Account>> accountsByClientId = accountRepository.getAccountsByClientId(clientId);
        return accountsByClientId.orElseGet(ArrayList::new);
    }

    @Override
    protected PagingAndSortingRepository<Account, Long> getDao() {
        return accountRepository;
    }

}
