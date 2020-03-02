package ru.sberbank.demo.app.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class TransferRequest {

    @Range(min = 0, message = "Неверное значение идентификатора счета отправителя")
    @NotNull
    private Long fromAccountId;

    @Range(min = 0, message = "Неверное значение идентификатора счета получателя")
    @NotNull
    private Long toAccountId;

    @Range(min = 1, message = "Перевод возможен для суммы от 1 у.е.")
    @NotNull
    private Long amount;

}
