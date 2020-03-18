package ru.sberbank.demo.app.service;

//import com.baeldung.common.persistence.exception.EntityNotFoundException;
//import com.baeldung.common.web.exception.BadRequestException;

import ru.sberbank.demo.app.exception.BadRequestException;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public final class ServicePreconditions {

    private ServicePreconditions() {
        throw new AssertionError();
    }

    // API

    /**
     * Ensures that the entity reference passed as a parameter to the calling method is not null.
     *
     * @param entity an object reference
     * @return the non-null reference that was validated
     * @throws EntityNotFoundException if {@code entity} is null
     */
    public static <T> T checkEntityExists(final Optional<T> entity) {
        if (!entity.isPresent()) {
            throw new EntityNotFoundException();
        }
        return entity.get();
    }

    public static void checkEntityExists(final boolean entityExists) {
        if (!entityExists) {
            throw new EntityNotFoundException();
        }
    }

    public static void checkOKArgument(final boolean okArgument) {
        if (!okArgument) {
            throw new BadRequestException();
        }
    }

}
