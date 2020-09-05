package ru.sberbank.demo.app.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.demo.app.model.transaction.TransferTransaction;

import java.util.UUID;

@Repository
public interface TransferTransactionRepository extends PagingAndSortingRepository<TransferTransaction, UUID>, JpaSpecificationExecutor<TransferTransaction> {
}
