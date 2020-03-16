package ru.sberbank.demo.app.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.demo.app.model.Client;

@Repository
public interface ClientsRepository extends PagingAndSortingRepository<Client, Long>, JpaSpecificationExecutor<Client> {
}
