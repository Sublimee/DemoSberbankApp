package ru.sberbank.demo.app.service.transaction.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.demo.app.exception.AccountNotFoundException;
import ru.sberbank.demo.app.exception.ResourceNotFoundException;
import ru.sberbank.demo.app.exception.transaction.TransferTransactionException;
import ru.sberbank.demo.app.exception.transaction.WithdrawTransactionException;
import ru.sberbank.demo.app.model.transaction.TransferTransaction;
import ru.sberbank.demo.app.repository.TransferTransactionRepository;
import ru.sberbank.demo.app.service.transaction.DepositTransactionService;
import ru.sberbank.demo.app.service.transaction.TransferTransactionService;
import ru.sberbank.demo.app.service.transaction.WithdrawTransactionService;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.UUID;

@Service
public class TransferServiceImpl implements TransferTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TransferTransactionRepository transferTransactionRepository;
    private final WithdrawTransactionService withdrawTransactionService;
    private final DepositTransactionService depositTransactionService;

    @Autowired
    public TransferServiceImpl(TransferTransactionRepository transferTransactionRepository, WithdrawTransactionService withdrawTransactionService, DepositTransactionService depositTransactionService) {
        this.transferTransactionRepository = transferTransactionRepository;
        this.withdrawTransactionService = withdrawTransactionService;
        this.depositTransactionService = depositTransactionService;
    }


    /**
     * Перевод денег со счета на счет
     *
     * @param fromAccountId  идентификатор счета отправителя
     * @param toAccountId    идентификатор счета получателя
     * @param transferAmount сумма перевода
     * @return информация о выполненной транзакции
     * @throws TransferTransactionException если задана некорректная сумма для перевода
     * @throws AccountNotFoundException     если один из заданных счетов не найден
     */
    @Override
    @Transactional
    public TransferTransaction transfer(final UUID fromAccountId, final UUID toAccountId, final Long transferAmount) throws ResourceNotFoundException {
        checkTransactionParams(fromAccountId, toAccountId, transferAmount);
        return getTransferTransaction(fromAccountId, toAccountId, transferAmount);
    }

    private TransferTransaction getTransferTransaction(final UUID fromAccountId, final UUID toAccountId, final Long transferAmount) throws ResourceNotFoundException {
        TransferTransaction transferTransaction = new TransferTransaction();
        try {
            transferTransaction.setAccount(withdrawTransactionService.getWithdrawAccount(fromAccountId, transferAmount));
        } catch (WithdrawTransactionException e) {
            logger.error(e.getMessage());
            throw new TransferTransactionException(e.getMessage());
        }
        transferTransaction.setPayee(depositTransactionService.getDepositAccount(toAccountId, transferAmount));
        transferTransaction.setTransferAmount(transferAmount);
        return transferTransactionRepository.save(transferTransaction);
    }

    private void checkTransactionParams(final UUID fromAccountId, final UUID toAccountId, final Long transferAmount) throws TransferTransactionException {
        if (fromAccountId.equals(toAccountId)) {
            logger.error("Переводы не осуществляются внутри одного счета: {}", fromAccountId);
            throw new TransferTransactionException("Переводы не осуществляются внутри одного счета");
        }
        if (transferAmount <= 0) {
            logger.error("Перевод возможен от суммы в 1 у.е. Задано: {} у.е.", transferAmount);
            throw new TransferTransactionException("Перевод возможен от суммы в 1 у.е. Задано: " + transferAmount + " у.е.");
        }
    }

}
