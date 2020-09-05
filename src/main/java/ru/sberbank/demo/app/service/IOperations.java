package ru.sberbank.demo.app.service;

import ru.sberbank.demo.app.exception.ResourceNotFoundException;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface IOperations<T extends Serializable> {

    T findOne(final UUID id) throws ResourceNotFoundException;

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAll();

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAllSorted(final String sortBy, final String sortOrder);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAllPaginated(final int page, final int size);

    /**
     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
     */
    List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder);

    T create(final T resource) throws Exception;

    void update(final T resource);

    void delete(final UUID id) throws ResourceNotFoundException;

    void deleteAll();

    long count();

}
