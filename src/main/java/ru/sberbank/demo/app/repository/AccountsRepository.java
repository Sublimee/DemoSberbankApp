package ru.sberbank.demo.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.demo.app.model.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {

    Optional<List<Account>> getAccountsByClient_Id(Long clientId);

    Optional<Account> getAccountById(Long id);

}
