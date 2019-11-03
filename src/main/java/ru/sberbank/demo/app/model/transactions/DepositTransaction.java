package ru.sberbank.demo.app.model.transactions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "deposits")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DepositTransaction extends AbstractTransaction {
}
