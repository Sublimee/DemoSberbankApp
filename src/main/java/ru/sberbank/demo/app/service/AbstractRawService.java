package ru.sberbank.demo.app.service;


import org.apache.commons.collections4.IterableUtils;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Transactional
public abstract class AbstractRawService<T extends Serializable> implements IRawService<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    protected ApplicationEventPublisher eventPublisher;

//    public AbstractRawService() {
//        super();
//    }

    // API

    // find - one

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findOne(final long id) {
        return getDao().findById(id);
    }

    // find - all

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return IterableUtils.toList(getDao().findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder) {
        return getDao().findAll(PageRequest.of(page, size, constructSort(sortBy, sortOrder)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder) {
        return getDao().findAll(PageRequest.of(page, size, constructSort(sortBy, sortOrder))).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAllPaginatedRaw(final int page, final int size) {
        return getDao().findAll(PageRequest.of(page, size));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllPaginated(final int page, final int size) {
        return getDao().findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAllSorted(final String sortBy, final String sortOrder) {
        final Sort sortInfo = constructSort(sortBy, sortOrder);
        return IterableUtils.toList(getDao().findAll(sortInfo));
    }

    // save/create/persist

    @Override
    public T create(final T entity) {
        checkNotNull(entity);

        return getDao().save(entity);
    }

    // update/merge

    @Override
    public void update(final T entity) {
        checkNotNull(entity);

        getDao().save(entity);
    }

    // delete

    @Override
    public void deleteAll() {
        getDao().deleteAll();
    }

    @Override
    public void delete(final long id) {
        final Optional<T> entity = getDao().findById(id);
        getDao().delete(ServicePreconditions.checkEntityExists(entity));
    }

    // count

    @Override
    public long count() {
        return getDao().count();
    }

    // template method

    protected abstract PagingAndSortingRepository<T, Long> getDao();

    protected abstract JpaSpecificationExecutor<T> getSpecificationExecutor();

    // template

    protected final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = null;
        if (sortBy != null) {
            sortInfo = Sort.by(Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
    }

    private <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

}
