package ru.sberbank.demo.app.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.demo.app.model.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long>, JpaSpecificationExecutor<Account> {

    Optional<List<Account>> getAccountsByClientId(final Long clientId);

    Optional<Account> getAccountById(final Long id);

}
