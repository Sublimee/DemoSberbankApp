package ru.sberbank.demo.app.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.demo.app.model.transactions.TransferTransaction;

@Repository
public interface TransferTransactionsRepository extends PagingAndSortingRepository<TransferTransaction, Long> {
}
