package ru.sberbank.demo.app.service;

import java.io.Serializable;

public interface IWithId extends Serializable {

    Long getId();

    void setId(final Long id);

}
