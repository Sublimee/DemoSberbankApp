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
import ru.sberbank.demo.app.model.request.TransferRequest;
import ru.sberbank.demo.app.model.transaction.TransferTransaction;
import ru.sberbank.demo.app.service.transaction.TransferTransactionService;

import javax.validation.Valid;

@RestController
@RequestMapping("/transfers")
@Validated
public class TransferController {

    private final TransferTransactionService transferTransactionService;

    @Autowired
    public TransferController(TransferTransactionService transferTransactionService) {
        this.transferTransactionService = transferTransactionService;
    }


    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TransferTransaction> transfer(@Valid @RequestBody final TransferRequest transferRequest) throws Exception {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transferTransactionService.transfer(transferRequest.getFromAccountId(), transferRequest.getToAccountId(), transferRequest.getAmount()));
    }

}
