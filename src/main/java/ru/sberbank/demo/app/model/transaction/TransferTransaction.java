package ru.sberbank.demo.app.model.transaction;


import ru.sberbank.demo.app.model.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transfers")
public class TransferTransaction extends AbstractTransaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payee_account_id", nullable = false)
    @NotNull
    private Account payee;

    public Account getPayee() {
        return payee;
    }

    public void setPayee(Account payee) {
        this.payee = payee;
    }
}
