package ru.sberbank.demo.app.model.transaction;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;
import ru.sberbank.demo.app.model.Account;
import ru.sberbank.demo.app.model.IEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
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

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Long transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractTransaction)) return false;
        AbstractTransaction that = (AbstractTransaction) o;
        return Objects.equals(id, that.id) && Objects.equals(account, that.account) && Objects.equals(transferAmount, that.transferAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, transferAmount);
    }
}
