package ru.sberbank.demo.app.service;



public abstract class AbstractService<T extends IEntity> extends AbstractRawService<T> implements IService<T> {

    public AbstractService() {
        super();
    }

    // API

}
