package ru.sberbank.demo.app.service.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.model.Client;
import ru.sberbank.demo.app.repository.AccountsRepository;
import ru.sberbank.demo.app.repository.ClientsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository;

    private final ClientsRepository clientsRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository, ClientsRepository clientsRepository) {
        this.accountsRepository = accountsRepository;
        this.clientsRepository = clientsRepository;
    }

    /**
     * Получение списка всех счетов клиента
     *
     * @param clientId идентификатор клиента
     * @return список счетов клиента
     * @throws ClientNotFoundException если клиент с заданным идентификатором не найден
     */
    @Override
    public List<Account> getClientAccounts(Long clientId) throws ClientNotFoundException, AccountNotFoundException {
        Optional<Client> client = clientsRepository.findById(clientId);
        if (!client.isPresent()){
            log.error("Клиент с идентификатором " + clientId + " не найден");
            throw new ClientNotFoundException();
        }
        Optional<List<Account>> accountsByClientId = accountsRepository.getAccountsByClientId(clientId);
        return accountsByClientId.orElseGet(ArrayList::new);
    }

    /**
     * Получение информации по конкретному счету
     *
     * @param accountId идентификатор счета
     * @return информация о счете
     * @throws AccountNotFoundException если счет с заданным идентификатором не найден
     */
    @Override
    public Account getAccountById(Long accountId) throws AccountNotFoundException {
        Optional<Account> account = accountsRepository.getAccountById(accountId);
        if (!account.isPresent()) {
            log.error("Счет с идентификатором " + accountId + " не найден");
            throw new AccountNotFoundException();
        }
        return account.get();
    }

}
