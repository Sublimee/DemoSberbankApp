package ru.sberbank.demo.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.demo.app.model.Client;

@Repository
public interface ClientsRepository extends CrudRepository<Client, Long> {
}
