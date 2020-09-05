package ru.sberbank.demo.app.service.user;

import ru.sberbank.demo.app.exception.EmailExistsException;
import ru.sberbank.demo.app.model.User;
import ru.sberbank.demo.app.model.dto.UserDto;
import ru.sberbank.demo.app.service.IPaginatedService;

public interface UserService extends IPaginatedService<User> {

    UserDto register(final User user) throws EmailExistsException;

}