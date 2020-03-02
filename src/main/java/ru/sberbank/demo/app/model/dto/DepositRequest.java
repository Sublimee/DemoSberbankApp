package ru.sberbank.demo.app.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class DepositRequest {

    @Range(min = 0, message = "Неверное значение идентификатора счета")
    @NotNull
    private Long accountId;

    @Range(min = 1, message = "Пополнение возможно для суммы от 1 у.е.")
    @NotNull
    private Long amount;

}
