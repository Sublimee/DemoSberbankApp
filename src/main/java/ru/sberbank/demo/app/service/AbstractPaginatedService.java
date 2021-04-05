package ru.sberbank.demo.app.service;


import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.model.IEntity;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public abstract class AbstractPaginatedService<T extends IEntity, E extends ResourceNotFoundException> implements IPaginatedService<T> {

    private Constructor<? extends E> ctor;

    public AbstractPaginatedService(Class<? extends E> impl) {
        try {
            this.ctor = impl.getConstructor(String.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(42);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public T findOne(final UUID id) throws ResourceNotFoundException {
        final Optional<T> resource = getDao().findById(id);
        if (resource.isEmpty()) {
            throw getInstanceOfE("Ресурс с идентификатором " + id + " не найден");
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
    public void delete(final UUID id) throws ResourceNotFoundException {
        getDao().delete(findOne(id));
    }

    @Override
    public long count() {
        return getDao().count();
    }

    protected abstract PagingAndSortingRepository<T, UUID> getDao();

    protected final Sort constructSort(final String sortBy, final String sortOrder) {
        Sort sortInfo = null;
        if (sortBy != null) {
            sortInfo = Sort.by(Direction.fromString(sortOrder), sortBy);
        }
        return sortInfo;
    }

    private E getInstanceOfE(String msg) {
        try {
            return ctor.newInstance(msg);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(42);
        }
        return null;
    }

}
