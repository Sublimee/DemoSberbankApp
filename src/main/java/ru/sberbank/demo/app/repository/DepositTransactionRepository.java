package ru.sberbank.demo.app.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.demo.app.model.transaction.DepositTransaction;

import java.util.UUID;

@Repository
public interface DepositTransactionRepository extends PagingAndSortingRepository<DepositTransaction, UUID>, JpaSpecificationExecutor<DepositTransaction> {
}
