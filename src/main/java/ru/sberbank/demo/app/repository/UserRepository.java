package ru.sberbank.demo.app.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.demo.app.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(final String email);

}
