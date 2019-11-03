package ru.sberbank.demo.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.demo.app.dto.DepositRequest;
import ru.sberbank.demo.app.dto.TransferRequest;
import ru.sberbank.demo.app.dto.WithdrawRequest;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.transaction.DepositTransactionException;
import ru.sberbank.demo.app.exception.transaction.TransferTransactionException;
import ru.sberbank.demo.app.exception.transaction.WithdrawTransactionException;
import ru.sberbank.demo.app.model.transactions.DepositTransaction;
import ru.sberbank.demo.app.model.transactions.TransferTransaction;
import ru.sberbank.demo.app.model.transactions.WithdrawTransaction;
import ru.sberbank.demo.app.service.transaction.TransactionsService;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping(value = "/deposit", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DepositTransaction> deposit(@Valid @RequestBody DepositRequest depositRequest) throws AccountNotFoundException, DepositTransactionException {
        DepositTransaction deposit = transactionsService.deposit(depositRequest.getAccountId(), depositRequest.getAmount());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(deposit);
    }

    @PostMapping(value = "/transfer", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TransferTransaction> transfer(@Valid @RequestBody TransferRequest transferRequest) throws AccountNotFoundException, TransferTransactionException, WithdrawTransactionException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionsService.transfer(transferRequest.getFromAccountId(), transferRequest.getToAccountId(), transferRequest.getAmount()));
    }

    @PostMapping(value = "/withdraw", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<WithdrawTransaction> depositAccount(@Valid @RequestBody WithdrawRequest withdrawRequest) throws AccountNotFoundException, WithdrawTransactionException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionsService.withdraw(withdrawRequest.getAccountId(), withdrawRequest.getAmount()));
    }

}
