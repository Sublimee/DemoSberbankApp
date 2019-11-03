package ru.sberbank.demo.app.model.transactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.sberbank.demo.app.model.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "transfers")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransferTransaction extends AbstractTransaction {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payee_account_id", nullable = false)
    @NotNull
    private Account payee;

}
