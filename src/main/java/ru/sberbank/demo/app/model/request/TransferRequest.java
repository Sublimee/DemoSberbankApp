package ru.sberbank.demo.app.model.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
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

}
