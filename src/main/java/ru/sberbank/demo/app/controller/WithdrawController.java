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
import ru.sberbank.demo.app.model.request.WithdrawRequest;
import ru.sberbank.demo.app.model.transaction.WithdrawTransaction;
import ru.sberbank.demo.app.service.transaction.WithdrawTransactionService;

import javax.validation.Valid;

@RestController
@RequestMapping("/withdraws")
@Validated
public class WithdrawController {

    private final WithdrawTransactionService withdrawTransactionService;

    @Autowired
    public WithdrawController(WithdrawTransactionService withdrawTransactionService) {
        this.withdrawTransactionService = withdrawTransactionService;
    }


    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<WithdrawTransaction> depositAccount(@Valid @RequestBody final WithdrawRequest withdrawRequest) throws Exception {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(withdrawTransactionService.withdraw(withdrawRequest.getAccountId(), withdrawRequest.getAmount()));
    }

}
