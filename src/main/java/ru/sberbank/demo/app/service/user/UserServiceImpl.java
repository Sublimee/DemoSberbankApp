package ru.sberbank.demo.app.service.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.demo.app.exception.EmailExistsException;
import ru.sberbank.demo.app.exception.UserNotFoundException;
import ru.sberbank.demo.app.model.User;
import ru.sberbank.demo.app.model.dto.UserDto;
import ru.sberbank.demo.app.repository.UserRepository;
import ru.sberbank.demo.app.service.AbstractPaginatedService;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl extends AbstractPaginatedService<User, UserNotFoundException> implements UserService {

    UserRepository userRepository;

    ModelMapper modelMapper;

    PasswordEncoder passwordEncoder;

    public UserServiceImpl() {
        super(UserNotFoundException.class);
    }

    @Autowired
    public void setUserRepository(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected PagingAndSortingRepository<User, UUID> getDao() {
        return userRepository;
    }

    @Override
    @Transactional
    public UserDto register(final User user) throws EmailExistsException {
        if (emailExist(user.getEmail())) {
            throw new EmailExistsException("There is an account with that email address: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = getDao().save(user);
        return modelMapper.map(registeredUser, UserDto.class);
    }

    private boolean emailExist(String email) {
        final Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }


}
