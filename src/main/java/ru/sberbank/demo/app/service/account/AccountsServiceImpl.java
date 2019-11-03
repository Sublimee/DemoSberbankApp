package ru.sberbank.demo.app.service.account;

import org.springframework.stereotype.Service;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.repository.AccountsRepository;

import java.util.List;

@Service
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
        return accountsRepository.getAccountsByClient_Id(clientId).orElseThrow(ClientNotFoundException::new);
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
        return accountsRepository.getAccountById(accountId).orElseThrow(AccountNotFoundException::new);
    }

}
