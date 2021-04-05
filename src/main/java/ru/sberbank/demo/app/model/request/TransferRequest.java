package ru.sberbank.demo.app.model.request;


import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.UUID;


public class TransferRequest {

    @Range(min = 0, message = "Неверное значение идентификатора счета отправителя")
    @NotNull
    private UUID fromAccountId;

    @Range(min = 0, message = "Неверное значение идентификатора счета получателя")
    @NotNull
    private UUID toAccountId;

    @Range(min = 1, message = "Перевод возможен для суммы от 1 у.е.")
    @NotNull
    private Long amount;

    public UUID getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(UUID fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public UUID getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(UUID toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
