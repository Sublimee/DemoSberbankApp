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
import ru.sberbank.demo.app.model.request.DepositRequest;
import ru.sberbank.demo.app.model.transaction.DepositTransaction;
import ru.sberbank.demo.app.service.transaction.DepositTransactionService;

import javax.validation.Valid;

@RestController
@RequestMapping("/deposits")
@Validated
public class DepositController {

    private final DepositTransactionService depositTransactionService;

    @Autowired
    public DepositController(DepositTransactionService depositTransactionService) {
        this.depositTransactionService = depositTransactionService;
    }


    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DepositTransaction> deposit(@Valid @RequestBody final DepositRequest depositRequest) throws Exception {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(depositTransactionService.deposit(depositRequest.getAccountId(), depositRequest.getAmount()));
    }

}
