package ru.sberbank.demo.app.model.transaction;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "withdraws")
public class WithdrawTransaction extends AbstractTransaction {
}
