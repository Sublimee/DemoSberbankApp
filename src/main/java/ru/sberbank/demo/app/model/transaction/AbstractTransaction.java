package ru.sberbank.demo.app.model.transaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import ru.sberbank.demo.app.model.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Data
@EqualsAndHashCode
abstract class AbstractTransaction {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull
    private Account account;

    @Column(name = "transfer_amount", nullable = false)
    @NotNull
    @Range(min = 1)
    private Long transferAmount;

}
