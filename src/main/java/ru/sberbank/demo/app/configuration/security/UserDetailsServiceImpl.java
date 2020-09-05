package ru.sberbank.demo.app.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sberbank.demo.app.model.User;
import ru.sberbank.demo.app.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String ROLE_USER = "ROLE_USER";

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
        org.springframework.security.core.userdetails.User user = null;
        try {
            user = new org.springframework.security.core.userdetails.User(
                    optionalUser.get().getEmail(),
                    optionalUser.get().getPassword(),
                    true,
                    true,
                    true,
                    true,
                    getAuthorities(ROLE_USER));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

}
