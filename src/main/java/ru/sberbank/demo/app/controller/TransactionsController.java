package ru.sberbank.demo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import ru.sberbank.demo.app.model.transaction.DepositTransaction;
import ru.sberbank.demo.app.model.transaction.TransferTransaction;
import ru.sberbank.demo.app.model.transaction.WithdrawTransaction;
import ru.sberbank.demo.app.service.transaction.TransactionService;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
@Validated
public class TransactionsController {

    private final TransactionService transactionsService;

    @Autowired
    public TransactionsController(final TransactionService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping(value = "/deposits", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DepositTransaction> deposit(@Valid @RequestBody final DepositRequest depositRequest) throws AccountNotFoundException, DepositTransactionException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionsService.deposit(depositRequest.getAccountId(), depositRequest.getAmount()));
    }

    @PostMapping(value = "/transfers", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TransferTransaction> transfer(@Valid @RequestBody final TransferRequest transferRequest) throws AccountNotFoundException, TransferTransactionException, WithdrawTransactionException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionsService.transfer(transferRequest.getFromAccountId(), transferRequest.getToAccountId(), transferRequest.getAmount()));
    }

    @PostMapping(value = "/withdraws", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<WithdrawTransaction> depositAccount(@Valid @RequestBody final WithdrawRequest withdrawRequest) throws AccountNotFoundException, WithdrawTransactionException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionsService.withdraw(withdrawRequest.getAccountId(), withdrawRequest.getAmount()));
    }

}
