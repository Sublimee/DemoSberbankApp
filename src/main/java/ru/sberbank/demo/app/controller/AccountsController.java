package ru.sberbank.demo.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ClientNotFoundException;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.service.account.AccountsService;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping("/clients/{id}")
    public List<Account> getClientAccounts(@PathVariable("id") Long clientId) throws ClientNotFoundException {
        return accountsService.getClientAccounts(clientId);
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable("id") Long id) throws AccountNotFoundException {
        return accountsService.getAccountById(id);
    }

}