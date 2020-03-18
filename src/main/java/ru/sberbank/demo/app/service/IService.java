package ru.sberbank.demo.app.service;

//import com.baeldung.common.interfaces.IByNameApi;
//import com.baeldung.common.interfaces.IWithName;

import java.io.Serializable;

public interface IService<T extends Serializable> extends IRawService<T>{

    //

}
