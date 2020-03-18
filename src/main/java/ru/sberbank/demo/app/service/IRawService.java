package ru.sberbank.demo.app.service;


import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface IRawService<T extends Serializable> extends IOperations<T> {

    Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder);

    Page<T> findAllPaginatedRaw(final int page, final int size);

}
