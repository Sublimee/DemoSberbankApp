package ru.sberbank.demo.app.service.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.repository.AccountsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    /**
     * Получение списка всех счетов клиента
     *
     * @param clientId идентификатор клиента
     * @return список счетов клиента
     * @throws ClientNotFoundException если клиент с заданным идентификатором не найден
     */
    @Override
    public List<Account> getClientAccounts(Long clientId) throws ClientNotFoundException {
        Optional<List<Account>> accountsByClientId = accountsRepository.getAccountsByClient_Id(clientId);
        if (!accountsByClientId.isPresent()) {
            throw new ClientNotFoundException();
        }
        return accountsByClientId.get();
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
        Optional<Account> accountById = accountsRepository.getAccountById(accountId);
        if (!accountById.isPresent()) {
            log.error("Счет с идентификатором" + accountId + "не найден");
            throw new AccountNotFoundException();
        }
        return accountById.get();
    }

}
