package ru.sberbank.demo.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.demo.app.model.transactions.DepositTransaction;

@Repository
public interface DepositTransactionsRepository extends CrudRepository<DepositTransaction, Long> {
}
