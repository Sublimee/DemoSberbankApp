package ru.sberbank.demo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.model.User;
import ru.sberbank.demo.app.service.user.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> find(@PathVariable("id") final UUID id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final UUID id) throws ResourceNotFoundException {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @PostMapping
//    public ResponseEntity<User> create(@Valid User user) {
//        return ResponseEntity.status(HttpStatus.OK).body(userService.create(user));
//    }

    @PutMapping
    public ResponseEntity<Void> update(@Valid User user) {
        userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
