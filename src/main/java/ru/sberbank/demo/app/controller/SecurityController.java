package ru.sberbank.demo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.demo.app.exception.EmailExistsException;
import ru.sberbank.demo.app.model.User;
import ru.sberbank.demo.app.model.dto.UserDto;
import ru.sberbank.demo.app.service.user.UserService;


@RestController
@RequestMapping("/")
public class SecurityController {

    private final UserService userService;

    @Autowired
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody User user) throws EmailExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.register(user));
    }

}
