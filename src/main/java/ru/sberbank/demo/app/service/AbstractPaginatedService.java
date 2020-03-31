package ru.sberbank.demo.app.service;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.model.IEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
public abstract class AbstractPaginatedService<T extends IEntity> implements IPaginatedService<T> {

    // find

    @Override
    @Transactional(readOnly = true)
    public T findOne(final long id) {
        Optional<T> resource = getDao().findById(id);
        if (!resource.isPresent()) {
            log.error("Ресурс с идентификатором " + id + " не найден");
            throw new ResourceNotFoundException("Ресурс с идентификатором " + id + " не найден");
        }
        return resource.get();
    }

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

    // save/create/persist/update/merge/delete

    @Override
    public T create(@NonNull final T entity) {
        return getDao().save(entity);
    }

    @Override
    public void update(@NonNull final T entity) {
        getDao().save(entity);
    }

    @Override
    public void deleteAll() {
        getDao().deleteAll();
    }

    @Override
    public void delete(final long id) {
        final Optional<T> entity = getDao().findById(id);
        if (!entity.isPresent()) {
            throw new EntityNotFoundException();
        }
        getDao().delete(entity.get());
    }

    // count

    @Override
    public long count() {
        return getDao().count();
    }

    protected abstract PagingAndSortingRepository<T, Long> getDao();

    protected final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = null;
        if (sortBy != null) {
            sortInfo = Sort.by(Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
    }

}
