package ru.sberbank.demo.app.model.transaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.model.IEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@MappedSuperclass
@Data
@EqualsAndHashCode
abstract class AbstractTransaction implements IEntity {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @NotNull
    private UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull
    private Account account;

    @Column(name = "transfer_amount", nullable = false)
    @NotNull
    @Range(min = 1)
    private Long transferAmount;

}
