package ru.sberbank.demo.app.model;

import java.io.Serializable;
import java.util.UUID;

public interface IEntity extends Serializable {

    UUID getId();

    void setId(final UUID id);

}
