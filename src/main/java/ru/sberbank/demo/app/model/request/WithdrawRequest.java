package ru.sberbank.demo.app.model.request;


import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.UUID;


public class WithdrawRequest {

    @Range(min = 0, message = "Неверное значение идентификатора счета")
    @NotNull
    private UUID accountId;

    @Range(min = 1, message = "Снятие возможно для суммы от 1 у.е.")
    @NotNull
    private Long amount;

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
