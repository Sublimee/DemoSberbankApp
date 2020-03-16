package ru.sberbank.demo.app.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.demo.app.model.transactions.DepositTransaction;

@Repository
public interface DepositTransactionsRepository extends PagingAndSortingRepository<DepositTransaction, Long>, JpaSpecificationExecutor<DepositTransaction> {
}
