package ru.sberbank.demo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.service.account.AccountService;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<List<Account>> getClientAccounts(@PathVariable("id") final Long clientId) throws ClientNotFoundException, AccountNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getClientAccounts(clientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") final Long id) throws AccountNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountById(id));
    }

}